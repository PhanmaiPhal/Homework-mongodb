package co.istad.mongodbproject.feature.coure;


import co.istad.mongodbproject.base.BasedMessage;
import co.istad.mongodbproject.domain.Category;
import co.istad.mongodbproject.domain.Course;
import co.istad.mongodbproject.feature.category.CategoryRepository;
import co.istad.mongodbproject.feature.coure.dto.*;
import co.istad.mongodbproject.mapper.CourseMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;


@Service
@Slf4j
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;

    private final CourseMapper courseMapper;

    private final CategoryRepository categoryRepository;


    @Override
    public void createNewCourse(CourseRequest courseRequest) {
        log.info("you here 1");

        Category category= categoryRepository.findByTitle(courseRequest.categoryName())
                .orElseThrow(()-> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Category has not been found!"
                ));

//        course.setCategory(category);
//        course.setInstructorName("Phanmai");
//        course.setIsDeleted(false);
//        course.setCategoryId(course.getCategoryId());
//        course.setContent("Welcome to c++");
//        course.setThumbnail("This is C++");
//        course.setInstructorName(null);

        Course course  = courseMapper.fromCourseRequest(courseRequest);
        course.setCategory(category);

        courseRepository.save(course);
//        log.info("you here 2");

    }

    @Override
    public CourseVisibility findCourseByVisibility(String id,CourseVisibility courseVisibility) {

        Course course = courseRepository.findByStatus(courseVisibility)
                .orElseThrow(()->new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Visibility has not been found!"
                ));

        course.setStatus(true);

        return courseMapper.toCourseVisibilityResponse(course);
    }


    @Override
    public Page<CourseResponse> findAllCourses(int page, int size) {

        // Validate page and size parameters
        if (page < 0) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Page number must be greater than or equal to zero!"
            );
        }
        if (size < 1) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Size must be greater than 1"
            );
        }

        // Create a PageRequest with sorting by course name
        Sort sortBy = Sort.by(Sort.Direction.ASC, "name");
        PageRequest pageRequest = PageRequest.of(page, size, sortBy);

        // Fetch all courses where deleted is false
        Page<Course> courses = courseRepository.findAllByIsDeletedIsFalse(pageRequest);

//        // Handle the case where no courses are found
//        if (courses.isEmpty()) {
//            throw new ResponseStatusException(
//                    HttpStatus.NOT_FOUND,
//                    "There are no courses!"
//            );
//        }

        // Map the Page<Course> to Page<CourseResponse>
        return courses.map(courseMapper::toCourseResponse);
    }


    @Override
    public CourseDetailResponse findCourseDetailById(String id) {

        Course course = courseRepository.findById(id)
                .orElseThrow(()-> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Course has not been found!"
                ));

        return courseMapper.toCourseDetailResponse(course);
    }

    @Override
    public CourseResponse updateCourseById(String id, CourseUpdateRequest courseUpdateRequest) {

        Course course = courseRepository.findById(id)
                .orElseThrow(()->new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Course has not been found!"
                ));

//        course.setCategory(category);
//        course.setInstructorName(course.getInstructorName());
//        course.setIsDeleted(false);
//        course.setContent(course.getContent());
//        course.setThumbnail(course.getThumbnail());

        courseMapper.updateCourseFormRequest(course, courseUpdateRequest);
        courseRepository.save(course);

        return courseMapper.toCourseResponse(course);

    }

    @Transactional
    @Override
    public BasedMessage disableCourseById(String id) {

        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Course not found!"
                ));
        course.setIsDeleted(true);

        return new BasedMessage("Course has been disabled!");
    }

    @Override
    public BasedMessage enableCourseById(String id) {

        Course course= courseRepository.findById(id)
                .orElseThrow(()-> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Course not found!"
                ));

        course.setIsDeleted(false);

        return new BasedMessage("Course has been enable!");
    }

    @Override
    public CourseThumbnailRequest findCourseById(String id, CourseThumbnailRequest courseThumbnailRequest) {

        Course course = courseRepository.findById(id).
                orElseThrow(()-> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Course Thumbnail not found!"
                ));

        return courseMapper.toCourseThumbnailResponse(course);
    }

    @Override
    public Page<CourseSnippetResponse> findAllPrivate(int page, int size) {

        Pageable pageable = PageRequest.of(page, size);

        List<Course> courses = courseRepository.findAll();

        List<CourseSnippetResponse> courseSnippetResponses = courses
                .stream()
                .filter(course -> course.getIsDrafted().equals(true))
                .map(courseMapper::toCourseSnippetResponse)
                .toList();

        return new PageImpl<>(courseSnippetResponses, pageable, courses.size());
    }

    @Override
    public Page<CourseSnippetResponse> findAllPublic(int page, int size) {

        Pageable pageable = PageRequest.of(page, size);

        List<Course> courses = courseRepository.findAll();

        List<CourseSnippetResponse> courseSnippetResponses = courses
                .stream()
                .filter(course -> course.getIsDrafted().equals(false))
                .map(courseMapper::toCourseSnippetResponse)
                .toList();
        return new PageImpl<>(courseSnippetResponses, pageable, courses.size());
    }

    @Override
    public List<Course> findFreeCourse(int page, int size) {
        return courseRepository.findByPrice(0);
    }


    @Override
    public CourseResponse findCourseBySlug(String slug) {

        Course course = courseRepository.findAllBySlug(slug)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Course in slug not found!"
                ));

        return courseMapper.toCourseResponse(course);
    }

    @Override
    public CourseResponse findByInstructorName(String instructorUserName) {

        Course course = courseRepository.findAllByInstructorName(instructorUserName)
                .orElseThrow(()-> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,"Instructor has not been found!"
                ));

        return courseMapper.toCourseResponse(course);
    }

    @Override
    public void deleteCourseById(String id) {

        Course course = courseRepository.findById(id)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Course not found!"));

        courseRepository.delete(course);

    }

}

