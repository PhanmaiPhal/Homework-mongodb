package co.istad.mongodbproject.mapper;

import co.istad.mongodbproject.domain.Category;
import co.istad.mongodbproject.domain.Course;
import co.istad.mongodbproject.feature.category.dto.CategoryUpdateRequest;
import co.istad.mongodbproject.feature.coure.dto.*;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CourseMapper {

    Course fromCourseRequest(CourseRequest courseRequest);

    List<CourseResponse> toCourseResponse(List<Course> courses);

    CourseResponse toCourseResponse(Course course);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateCourseFormRequest(@MappingTarget Course course, CourseUpdateRequest courseUpdateRequest);

    CourseDetailResponse toCourseDetailResponse(Course course);

    CourseThumbnailRequest toCourseThumbnailResponse(Course course);

    CourseVisibility toCourseVisibilityResponse(Course course);

    CourseSnippetResponse toCourseSnippetResponse(Course course);
}
