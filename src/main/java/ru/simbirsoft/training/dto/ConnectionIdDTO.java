package ru.simbirsoft.training.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConnectionIdDTO {

    private Long id;
    private Long user_id;
    private Long room_id;

}
