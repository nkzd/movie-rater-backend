package io.vukotic.imdb.movierater.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.List;

@Document(indexName = "releases")
@Getter
@Setter
@NoArgsConstructor
public class Release {
    @Id
    private String id;

    @Field(type = FieldType.Keyword, name = "type")
    private String type;

    @Field(type = FieldType.Text, name = "title")
    private String title;

    @Field(type = FieldType.Text, name = "image")
    private String image;

    @Field(type = FieldType.Text, name = "description")
    private String description;

    @Field(type = FieldType.Integer, name = "year")
    private Integer year;

    @Field(type = FieldType.Double, name = "averageRating")
    private Double averageRating;

    @Field(type = FieldType.Integer, name = "numberOfRatings")
    private Integer numberOfRatings;



    @Field(type = FieldType.Text, name = "actors")
    private List<String> actors;
}
