package ru.simbirsoft.training.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.simbirsoft.training.domain.Connection;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageDTO {

    private Long id;
    private Connection connection;
    private String text;
    private Date sendTime;

}
