package ru.simbirsoft.trainingSimbirsoft.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.simbirsoft.trainingSimbirsoft.domain.Message;
import ru.simbirsoft.trainingSimbirsoft.dto.MessageDTO;
import ru.simbirsoft.trainingSimbirsoft.mapper.MessageMapper;
import ru.simbirsoft.trainingSimbirsoft.repository.MessageRepository;
import ru.simbirsoft.trainingSimbirsoft.service.MessageService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;

    @Override
    public List<MessageDTO> getAll() {
        return allToDTO(messageRepository.findAll());
    }

    @Override
    public MessageDTO findMessageById(Long id) {
        return toDTO(messageRepository.getById(id));
    }

    @Override
    public MessageDTO createMessage(MessageDTO messageDTO) {
        messageRepository.save(toEntity(messageDTO));
        return toDTO(messageRepository.getById(messageDTO.getId()));
    }

    @Override
    public MessageDTO editMessage(MessageDTO messageDTO) {
        if (messageRepository.findById(messageDTO.getId()).isPresent()){
            createMessage(messageDTO);
        }
        return toDTO(messageRepository.getById(messageDTO.getId()));
    }

    @Override
    public boolean deleteMessageById(Long id) {
        if(messageRepository.findById(id).isPresent()) {
            messageRepository.deleteById(id);
        }
        return (messageRepository.findById(id).isPresent());
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
