package ru.simbirsoft.trainingSimbirsoft.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.simbirsoft.trainingSimbirsoft.domain.Message;
import ru.simbirsoft.trainingSimbirsoft.domain.User_Room;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageDto {

    private long id;

    private User_Room user_room;

    private String text;

    private Date sendTime;

    public static MessageDto fromEntityToModel(Message message){
        MessageDto messageDto = new MessageDto();
        messageDto.setId(message.getId());
        messageDto.setUser_room(message.getUser_room());
        messageDto.setText(message.getText());
        messageDto.setSendTime(message.getSendTime());
        return messageDto;
    }
}
