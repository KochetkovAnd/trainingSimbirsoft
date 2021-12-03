package ru.simbirsoft.trainingSimbirsoft.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
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

}
