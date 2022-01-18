package ru.simbirsoft.training.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.simbirsoft.training.domain.User;
import ru.simbirsoft.training.domain.enums.RoomType;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomDTO {

    private Long id;
    private String name;
    private RoomType type;
    private User owner;

}
