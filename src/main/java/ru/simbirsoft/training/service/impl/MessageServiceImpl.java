package ru.simbirsoft.training.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.simbirsoft.training.domain.Message;
import ru.simbirsoft.training.dto.MessageDTO;
import ru.simbirsoft.training.exceptions.MessageNotFoundException;
import ru.simbirsoft.training.mapper.MessageMapper;
import ru.simbirsoft.training.repository.MessageRepository;
import ru.simbirsoft.training.service.MessageService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;

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
        throw new MessageNotFoundException(id);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public MessageDTO create(MessageDTO messageDTO) {
        messageRepository.save(toEntity(messageDTO));
        return messageDTO;
    }

    @Override
    @Transactional
    public MessageDTO update(MessageDTO messageDTO) {
        if (messageRepository.findById(messageDTO.getId()).isPresent()){
            create(messageDTO);
            return messageDTO;
        }
        throw new MessageNotFoundException(messageDTO.getId());
    }

    @Override
    @Transactional
    public boolean deleteById(Long id) {
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
