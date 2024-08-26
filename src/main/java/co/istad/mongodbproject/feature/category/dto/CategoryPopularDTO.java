package co.istad.mongodbproject.feature.category.dto;

public record CategoryPopularDTO(
        String id,

        String name,

        String icon,

        Integer totalCourse
) {
}
