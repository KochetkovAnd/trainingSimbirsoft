package ru.simbirsoft.trainingSimbirsoft.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.simbirsoft.trainingSimbirsoft.domain.User;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomDto {

    private long id;
    private String name;
    private String type;
    private User owner;

}
