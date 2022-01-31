package ru.simbirsoft.training.dto;

import lombok.Data;

import java.util.Date;

@Data
public class Videos {

    private String id;
    private String title;
    private Date published;
    private long viewCount;
    private long likeCount;

}
