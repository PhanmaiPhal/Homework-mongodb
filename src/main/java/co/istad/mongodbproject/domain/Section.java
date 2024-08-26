package co.istad.mongodbproject.domain;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Section {

        private String title;

        private Integer orderNo;

        private List<Video> videos;
    }

