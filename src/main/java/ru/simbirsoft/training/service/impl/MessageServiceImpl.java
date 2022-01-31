package ru.simbirsoft.training.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.simbirsoft.training.domain.Message;
import ru.simbirsoft.training.domain.User;
import ru.simbirsoft.training.dto.MessageDTO;
import ru.simbirsoft.training.exceptions.NoPermissionException;
import ru.simbirsoft.training.exceptions.ResourceNotFoundException;
import ru.simbirsoft.training.mapper.MessageMapper;
import ru.simbirsoft.training.repository.ConnectionRepository;
import ru.simbirsoft.training.repository.MessageRepository;
import ru.simbirsoft.training.repository.RoomRepository;
import ru.simbirsoft.training.repository.UserRepository;
import ru.simbirsoft.training.service.MessageService;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final UserRepository userRepository;
    private final RoomRepository roomRepository;
    private final ConnectionRepository connectionRepository;
    private final MessageRepository messageRepository;

    private final UserServiceImpl userService;
    private final ConnectionServiceImpl connectionService;

    @Override
    @Transactional(readOnly = true)
    public List<MessageDTO> getAll() {
        return allToDTO(messageRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public MessageDTO getById(Long id) {
        if (messageRepository.findById(id).isPresent()) {
            return toDTO(messageRepository.getById(id));
        }
        throw new ResourceNotFoundException("No message found with id = " + id, "");
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public MessageDTO create(MessageDTO messageDTO) {
        if (connectionRepository.findById(messageDTO.getConnection().getId()).isPresent()) {
            messageRepository.save(toEntity(messageDTO));
            return messageDTO;
        }
        throw new ResourceNotFoundException("No connection found with id = " + messageDTO.getConnection().getId(), "");
    }

    @Override
    @Transactional
    public MessageDTO update(MessageDTO messageDTO) {
        if (messageRepository.findById(messageDTO.getId()).isPresent()){
            create(messageDTO);
            return messageDTO;
        }
        throw new ResourceNotFoundException("No message found with id = " + messageDTO.getId(), "");
    }

    @Override
    @Transactional
    @PreAuthorize("hasAuthority('message:remove')")
    public boolean deleteById(Long id) {
        if(messageRepository.findById(id).isPresent()) {
            messageRepository.deleteById(id);
            return (messageRepository.findById(id).isPresent());
        }
        throw new ResourceNotFoundException("No message found with id = " + id, "");
    }

    @Override
    @PreAuthorize("hasAuthority('message:send')")
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public MessageDTO createByRoomName(String roomName, String text) {
        if (roomRepository.findByName(roomName).isPresent()) {
            UserDetails securityUser = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User user = userRepository.findByName(securityUser.getUsername()).get();
            if (connectionRepository.findByUserAndRoom(user, roomRepository.findByName(roomName).get()).isPresent()) {
                if (!(connectionService.checkDisconnect(user, roomName)) && (!userService.checkBan(user))) {
                    MessageDTO messageDTO = new MessageDTO(
                            null,
                            connectionRepository.findByUserAndRoom(user, roomRepository.findByName(roomName).get()).get(),
                            text,
                            new Date()
                    );
                    messageRepository.save(toEntity(messageDTO));
                    return messageDTO;
                }
                throw new NoPermissionException("you are disconnect from room named = " + roomName + " or banned", "");
            }
            throw new ResourceNotFoundException("No connection  with roomName = " + roomName + " userName = " + user.getName() , "");
        }
        throw new ResourceNotFoundException("No room found with name = " + roomName, "");

    }

    @Override
    @Transactional(readOnly = true)
    @PreAuthorize("hasAuthority('message:get')")
    public List<MessageDTO> getFromRoom(String roomName) {
        if (roomRepository.findByName(roomName).isPresent()) {
            UserDetails securityUser = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User user = userRepository.findByName(securityUser.getUsername()).get();
            if (connectionRepository.findByUserAndRoom(user, roomRepository.findByName(roomName).get()).isPresent()) {
                return allToDTO(messageRepository.findMessagesByConnection(
                        connectionRepository.findByUserAndRoom(
                                user,
                                roomRepository.findByName(roomName).get()
                        ).get()
                ));
            }

        }
        throw new ResourceNotFoundException("No room found with name = " + roomName, "");
    }

    private Message toEntity(MessageDTO messageDTO){
        return MessageMapper.MESSAGE_MAPPER.toEntity(messageDTO);
    }

    private MessageDTO toDTO(Message message){
        return MessageMapper.MESSAGE_MAPPER.toDTO(message);
    }

    private List<Message> allToEntity(List<MessageDTO> messageDTOList){
        return MessageMapper.MESSAGE_MAPPER.allToEntity(messageDTOList);
    }

    private List<MessageDTO> allToDTO(List<Message> messageList){
        return MessageMapper.MESSAGE_MAPPER.allToDTO(messageList);
    }
}
