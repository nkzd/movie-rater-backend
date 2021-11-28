package io.vukotic.imdb.movierater.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class ReleaseDto {
    String id;
    String type;
    Integer year;
    Integer numberOfRatings;
    Double averageRating;
    String title;
    List<String> actors;
    String image;
    String description;
}
