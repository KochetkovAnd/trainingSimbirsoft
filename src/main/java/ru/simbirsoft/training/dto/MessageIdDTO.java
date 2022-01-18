package ru.simbirsoft.training.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageIdDTO {

    private Long id;
    private Long connection_id;
    private String text;
}