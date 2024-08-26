package co.istad.mongodbproject.feature.category.dto;

public record CategoryResponse(

        String id,

        String title,

        String icon,

        String description,

        Boolean isDeleted
) {
}
