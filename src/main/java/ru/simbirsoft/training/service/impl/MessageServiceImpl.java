package ru.simbirsoft.training.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.simbirsoft.training.domain.Connection;
import ru.simbirsoft.training.domain.Message;
import ru.simbirsoft.training.domain.Room;
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
        if (!(messageRepository.findById(id).isPresent())) {
            throw new ResourceNotFoundException("Message with id = " + id + " not found", "");
        }
        return toDTO(messageRepository.getById(id));
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public MessageDTO create(MessageDTO messageDTO) {
        if (!(connectionRepository.findById(messageDTO.getConnection().getId()).isPresent())) {
            throw new ResourceNotFoundException("Connection with id = " + messageDTO.getConnection().getId() + " not found", "");
        }
        messageRepository.save(toEntity(messageDTO));
        return messageDTO;
    }

    @Override
    @Transactional
    public MessageDTO update(MessageDTO messageDTO) {
        if (!(messageRepository.findById(messageDTO.getId()).isPresent())) {
            throw new ResourceNotFoundException("Message with id = " + messageDTO.getId() + " not found", "");
        }
        return create(messageDTO);
    }

    @Override
    @Transactional
    @PreAuthorize("hasAuthority('message:remove')")
    public boolean deleteById(Long id) {
        if (!(messageRepository.findById(id).isPresent())) {
            throw new ResourceNotFoundException("Message with id = " + id + " not found", "");
        }
        messageRepository.deleteById(id);
        return (messageRepository.findById(id).isPresent());
    }

    @Override
    @PreAuthorize("hasAuthority('message:send')")
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public MessageDTO createByRoomName(String roomName, String text) {
        if (!(roomRepository.findByName(roomName).isPresent())) {
            throw new ResourceNotFoundException("Room with name = " + roomName + " not found", "");
        }
        Room room = roomRepository.findByName(roomName).get();
        UserDetails securityUser = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByName(securityUser.getUsername()).get();
        if (!(connectionRepository.findByUserAndRoom(user, room).isPresent())) {
            throw new ResourceNotFoundException("Connection with userName = " + user.getName() + " and roomName = " + roomName+ " not found", "");
        }
        Connection connection = connectionRepository.findByUserAndRoom(user, room).get();
        if (connectionService.checkDisconnect(user, room) || userService.checkBan(user)) {
            throw new NoPermissionException("You are disconnect from room named = " + roomName + " or banned globally", "");
        }
        MessageDTO messageDTO = new MessageDTO(null, connection, text, new Date());
        messageRepository.save(toEntity(messageDTO));
        return messageDTO;
    }

    @Override
    @Transactional(readOnly = true)
    @PreAuthorize("hasAuthority('message:get')")
    public List<MessageDTO> getFromRoom(String roomName) {
        if (!(roomRepository.findByName(roomName).isPresent())) {
            throw new ResourceNotFoundException("Room with name = " + roomName + " not found", "");
        }
        Room room = roomRepository.findByName(roomName).get();
        UserDetails securityUser = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByName(securityUser.getUsername()).get();

        if (!(connectionRepository.findByUserAndRoom(user,room).isPresent())) {
            throw new ResourceNotFoundException("Connection with userName = " + user.getName() + " and roomName = " + roomName+ " not found", "");
        }
        Connection connection = connectionRepository.findByUserAndRoom(user, room).get();
        return allToDTO(messageRepository.findMessagesByConnection(connection));
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

