package co.istad.mongodbproject.feature.category.dto;

public record CategoryUpdateRequest(

        String title,

        String icon,

        String description
) {
}
