package co.istad.mongodbproject.feature.coure.dto;

public record CourseResponse(
        String title ,

        String slug,

        String description,

        String thumbnail,

        Double price,

        String content,

        String categoryName,

        String instructorName

) {
}
