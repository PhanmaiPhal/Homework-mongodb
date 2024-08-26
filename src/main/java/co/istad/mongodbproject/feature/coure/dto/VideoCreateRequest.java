package co.istad.mongodbproject.feature.coure.dto;

public record VideoCreateRequest(

        Integer sectionOrderNo,

        String lectureNo,

        Integer orderNo,

        String title,

        String fileName
) {
}
