package co.istad.mongodbproject.feature.coure.dto;

public record CourseDetailResponse(
        String title,
        String description,
        String thumbnail
) {
}
