package co.istad.mongodbproject.feature.coure.dto;

public record CourseSnippetResponse(

        String id,

        String title ,

        String slug,

        Double price ,

        Double discount,

        Boolean isPaid,

        Boolean isDrafted,

        String thumbnail
) {
}
