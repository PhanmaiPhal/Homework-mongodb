package co.istad.mongodbproject.feature.coure;

import co.istad.mongodbproject.base.BasedMessage;
import co.istad.mongodbproject.domain.Course;
import co.istad.mongodbproject.feature.coure.dto.*;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.connector.Response;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/courses")
public class CourseController {

    private final CourseService courseService;

    @GetMapping
    public Page<CourseResponse> findAllCourses(@RequestParam(defaultValue = "0", required = false)int page,
                                               @RequestParam(defaultValue = "25",required = false)int size){
        return courseService.findAllCourses(page,size);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createNewCourse(@Valid @RequestBody CourseRequest courseRequest){
        courseService.createNewCourse(courseRequest);
    }

    @GetMapping("/{id}")
    public CourseDetailResponse findCourseDetailById(@PathVariable String id){
        return courseService.findCourseDetailById(id);
    }

    @PutMapping("/{id}")
    public CourseResponse updateCourseById(@PathVariable String id,@Valid @RequestBody CourseUpdateRequest courseUpdateRequest){
        return courseService.updateCourseById(id,courseUpdateRequest);
    }

    @PutMapping("/{id}/disable")
    public BasedMessage disableById(@PathVariable String id){
        return courseService.disableCourseById(id);
    }

    @PutMapping("/{id}/enable")
    public BasedMessage enableCourseById(@PathVariable String id){
        return courseService.enableCourseById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteCourseById(@PathVariable String id){
        courseService.deleteCourseById(id);
    }
    @PutMapping("/{id}/thumbnail")
    public CourseThumbnailRequest findCourseById(@PathVariable String id, @Valid @RequestBody CourseThumbnailRequest courseThumbnailRequest){
        return courseService.findCourseById(id, courseThumbnailRequest);
    }

    @GetMapping("/{slug}/slug")
    public CourseResponse findCourseBySlug(@PathVariable String slug){
        return courseService.findCourseBySlug(slug);
    }

    @PutMapping("/{id}/visibilities")
    public CourseVisibility findCourseVisibility(@PathVariable String id,@Valid @RequestBody CourseVisibility courseVisibility){
        return courseService.findCourseByVisibility(id,courseVisibility);
    }

    @GetMapping("/instructor/{instructor}")
    public CourseResponse findInstructorByName(@PathVariable String instructorName){
        return courseService.findByInstructorName(instructorName);
    }

    @GetMapping("/public")
    public Page<CourseSnippetResponse> findAllPublic(@RequestParam(defaultValue = "0", required = false)int page
                                                    ,@RequestParam(defaultValue = "25",required = false)int size){
        return courseService.findAllPublic(page,size);
    }

    @GetMapping("/private")
    public Page<CourseSnippetResponse> findAllPrivate(@RequestParam(defaultValue = "0", required = false)int page ,
                                                      @RequestParam(defaultValue = "25", required = false)int size){
        return courseService.findAllPrivate(page,size);
    }

    @GetMapping("/free")
    public List<Course> findByPrice(@RequestParam(defaultValue = "0",required = false)int page,
                                    @RequestParam(defaultValue = "25", required = false)int size){
        return courseService.findFreeCourse(page, size);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{id}/sections")
    void createCourseSection(@PathVariable String id, @Valid @RequestBody SectionCreateRequest sectionCreateRequest) {
        courseService.createVideoInSection(id, sectionCreateRequest);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{courseId}/videos")
    void createVideoInSection(@PathVariable String courseId, @Valid @RequestBody VideoCreateRequest videoCreateRequest) {
        courseService.createVideoInSection(courseId, videoCreateRequest);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{courseId}/videos")
    void updateVideoInSection(@PathVariable String courseId, @Valid @RequestBody VideoUpdateRequest videoUpdateRequest) {
        courseService.updateVideoInSection(courseId, videoUpdateRequest);
    }

    @GetMapping("/advancedSearch/params")
    Page<?> advancedSearchCourse(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String filterAnd,
            @RequestParam(required = false) String filterOr,
            @RequestParam(required = false) String orders,
            @RequestParam(defaultValue = "COURSE_SNIPPET") Response response
    ) {
        return courseService.advancedSearchCourseParam(page, size, filterAnd, filterOr, orders, response.name());
    }

    Page<?> advancedSearchCourse(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @Valid @RequestBody FilterDTO filterDTO,
            @RequestParam(defaultValue = "COURSE_SNIPPET") Response response
    ) {
        return courseService.advancedSearchCourseRequestBody(page, size, filterDTO, response.name());
    }

}

