package co.istad.mongodbproject.feature.category.dto;

public record CategoryCreateRequest(
        String title,
        String description,
        String icon
) {
}
