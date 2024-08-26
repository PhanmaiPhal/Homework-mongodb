package co.istad.mongodbproject.feature.coure.dto;

import co.istad.mongodbproject.domain.Video;

import java.util.List;

public record VideoUpdateRequest(

        Integer sectionOrderNo,

        List<Video> videos
) {
}
