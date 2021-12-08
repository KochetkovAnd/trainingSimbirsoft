package ru.simbirsoft.trainingSimbirsoft.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.simbirsoft.trainingSimbirsoft.domain.Message;
import ru.simbirsoft.trainingSimbirsoft.dto.MessageDTO;

import java.util.List;

@Mapper
public interface MessageMapper {

    MessageMapper MESSAGE_MAPPER = Mappers.getMapper(MessageMapper.class);

    Message toEntity(MessageDTO messageDTO);

    @Mapping(target = "id", ignore = true)
    MessageDTO toDTO(Message message);
    List<Message> allToEntity(List<MessageDTO> messageDTOList);
    List<MessageDTO> allToDTO(List<Message> messageList);
}
