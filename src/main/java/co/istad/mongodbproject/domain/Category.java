package co.istad.mongodbproject.domain;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@NoArgsConstructor
@Getter
@Setter
@Document(collection = "categories")
public class Category {
    @Id
    private String id;

    private String title ;

    private String icon ;

    private String description ;

    private Boolean isDeleted;

    private Course course;

}
