package co.istad.mongodbproject.feature.coure;

import co.istad.mongodbproject.base.BasedMessage;
import co.istad.mongodbproject.domain.Course;
import co.istad.mongodbproject.feature.coure.dto.*;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CourseService {

    //Create course
    void createNewCourse(CourseRequest courseRequest);

    //Find course by slug
    CourseResponse findCourseBySlug(String slug);

    //Find course by instructor's name
    CourseResponse findByInstructorName(String instructorUserName);

    CourseVisibility findCourseByVisibility(String id,CourseVisibility courseVisibility);

    //Find all courses
    Page<CourseResponse> findAllCourses(int page , int size);

    //Find course detail by id
    CourseDetailResponse findCourseDetailById(String id);

    //Update course by id
    CourseResponse updateCourseById(String id, CourseUpdateRequest courseUpdateRequest);

    //Disable course by id
    BasedMessage disableCourseById(String id);

    //Delete course by id
    void deleteCourseById(String id);

    //Enable course by id
    BasedMessage enableCourseById(String id);

    //Update thumbnail by courseId
    CourseThumbnailRequest findCourseById(String id, CourseThumbnailRequest courseThumbnailRequest);

    //Get private courses
    Page<CourseSnippetResponse> findAllPrivate(int page , int size);

    //Get public courses
    Page<CourseSnippetResponse> findAllPublic(int page , int size);

    //Get free courses
    List<Course> findFreeCourse(int page , int size);

    //Get is-paid
    void updateIsPaid(String id, Boolean isPaid);

    //Get create video in section
    void createVideoInSection(String courseId, VideoCreateRequest videoCreateRequest);

    //Get update video in section
    void updateVideoInSection(String courseId, VideoUpdateRequest videoUpdateRequest);

    Page<?> advancedSearchCourseParam(int page, int size, String filterAnd, String filterOr, String orders, String response);

    Page<?> advancedSearchCourseRequestBody(int page, int size, FilterDTO filterDTO, String response);


}
