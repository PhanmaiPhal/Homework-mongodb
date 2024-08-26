package co.istad.mongodbproject.domain;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class Course {

    private String id;

    private String title;

    private String description;

    private String slug;

    private Boolean isPaid;

    private Double price;

    private Boolean isFree;

    private Boolean isDeleted;

    private String thumbnail;

    private String isDrafted;

    private String instructorName;

    private String categoryId;

    private Boolean status;

    private String content;

    private Category category;

    private List<Section> sections;

}
