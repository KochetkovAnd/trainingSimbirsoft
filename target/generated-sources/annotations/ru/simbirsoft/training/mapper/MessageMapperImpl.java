package ru.simbirsoft.training.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import ru.simbirsoft.training.domain.Message;
import ru.simbirsoft.training.dto.MessageDTO;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-12-15T21:58:27+0400",
    comments = "version: 1.3.1.Final, compiler: javac, environment: Java 11.0.11 (JetBrains s.r.o.)"
)
public class MessageMapperImpl implements MessageMapper {

    @Override
    public Message toEntity(MessageDTO messageDTO) {
        if ( messageDTO == null ) {
            return null;
        }

        Message message = new Message();

        message.setId( messageDTO.getId() );
        message.setConnection( messageDTO.getConnection() );
        message.setText( messageDTO.getText() );
        message.setSendTime( messageDTO.getSendTime() );

        return message;
    }

    @Override
    public MessageDTO toDTO(Message message) {
        if ( message == null ) {
            return null;
        }

        MessageDTO messageDTO = new MessageDTO();

        messageDTO.setConnection( message.getConnection() );
        messageDTO.setText( message.getText() );
        messageDTO.setSendTime( message.getSendTime() );

        return messageDTO;
    }

    @Override
    public List<Message> allToEntity(List<MessageDTO> messageDTOList) {
        if ( messageDTOList == null ) {
            return null;
        }

        List<Message> list = new ArrayList<Message>( messageDTOList.size() );
        for ( MessageDTO messageDTO : messageDTOList ) {
            list.add( toEntity( messageDTO ) );
        }

        return list;
    }

    @Override
    public List<MessageDTO> allToDTO(List<Message> messageList) {
        if ( messageList == null ) {
            return null;
        }

        List<MessageDTO> list = new ArrayList<MessageDTO>( messageList.size() );
        for ( Message message : messageList ) {
            list.add( toDTO( message ) );
        }

        return list;
    }
}
