package co.istad.mongodbproject.feature.category.dto;

import jakarta.validation.constraints.NotBlank;

public record CategoryRequest(

//        @NotBlank(message = "id is require")
//        String id,

        String title,

        String icon,

        String description
) {
}
