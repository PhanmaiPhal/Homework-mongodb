package co.istad.mongodbproject.feature.coure;


import co.istad.mongodbproject.base.BasedMessage;
import co.istad.mongodbproject.domain.Category;
import co.istad.mongodbproject.domain.Course;
import co.istad.mongodbproject.domain.Video;
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
import java.util.ArrayList;
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
    public void updateIsPaid(String id, Boolean isPaid) {

        Course course = courseRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Course not found..!"
                )
        );
        course.setIsPaid(isPaid);
        courseRepository.save(course);

    }

    @Override
    public void createVideoInSection(String courseId, VideoCreateRequest videoCreateRequest) {

        // Step 1: Find the Course
        Course course = courseRepository.findById(courseId).orElseThrow(
                () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Course not found..!"
                )
        );

        // Step 2: Find the Section by sectionOrderNo
        List<Section> sections = course.getSections();
        if (sections == null || sections.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "No sections available in the course..!"
            );
        }

        Integer sectionOrderNo = videoCreateRequest.sectionOrderNo();
        Section targetSection = sections.stream()
                .filter(section -> section.getOrderNo().equals(sectionOrderNo))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Section with order number " + sectionOrderNo + " not found..!"
                ));

        // Step 3: Create a New Video from VideoCreateRequest
        Video newVideo = Video.builder()
                .lectureNo(videoCreateRequest.lectureNo())
                .orderNo(videoCreateRequest.orderNo())
                .title(videoCreateRequest.title())
                .fileName(videoCreateRequest.fileName())
                .build();

        // Step 4: Check for Unique orderNo in Section's Videos List
        List<Video> videos = targetSection.getVideos();
        if (videos == null) {
            videos = new ArrayList<>();
            targetSection.setVideos(videos);
        } else {
            boolean isDuplicate = videos.stream()
                    .anyMatch(video -> video.getOrderNo().equals(newVideo.getOrderNo()));
            if (isDuplicate) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Video order number must be unique within the section..!"
                );
            }
        }

        // Add the Video to the Section's Videos List
        videos.add(newVideo);

        // Step 5: Save the Updated Course
        courseRepository.save(course);

    }

    @Override
    public void updateVideoInSection(String courseId, VideoUpdateRequest videoUpdateRequest) {

        // Step 1: Find the Course
        Course course = courseRepository.findById(courseId).orElseThrow(
                () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Course not found..!"
                )
        );

        // Step 2: Find the Section by sectionOrderNo
        List<Section> sections = course.getSections();
        if (sections == null || sections.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "No sections available in the course..!"
            );
        }

        Integer sectionOrderNo = videoUpdateRequest.sectionOrderNo();
        Section targetSection = sections.stream()
                .filter(section -> section.getOrderNo().equals(sectionOrderNo))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Section with order number " + sectionOrderNo + " not found..!"
                ));

        // Step 3: Ensure unique orderNo for videos
        List<Video> updatedVideos = videoUpdateRequest.videos().stream()
                .collect(Collectors.toMap(
                        Video::getOrderNo,  // Key: orderNo
                        video -> video,     // Value: Video
                        (existing, replacement) -> existing  // Merge function to handle duplicates
                ))
                .values()
                .stream()
                .sorted(Comparator.comparingInt(Video::getOrderNo)) // Optional: Sort by orderNo
                .collect(Collectors.toList());

        // Step 4: Set the videos in the target section
        targetSection.setVideos(updatedVideos);

        // Step 5: Save the Updated Course
        courseRepository.save(course);

    }


    @Override
    public Page<?> advancedSearchCourseParam(int page, int size, String filterAnd, String filterOr, String orders, String response) {
        log.info("Searching students with filterAnd: {}, filterOr: {}, orders: {}", filterAnd, filterOr, orders);

        Query query = new Query();

        // Add AND filters
        if (filterAnd != null && !filterAnd.isEmpty()) {
            List<Criteria> andCriteria = parseFilterCriteria(filterAnd);
            query.addCriteria(new Criteria().andOperator(andCriteria.toArray(new Criteria[0])));
        }

        // Add OR filters
        if (filterOr != null && !filterOr.isEmpty()) {
            List<Criteria> orCriteria = parseFilterCriteria(filterOr);
            query.addCriteria(new Criteria().orOperator(orCriteria.toArray(new Criteria[0])));
        }

        // Add sorting
        if (orders != null && !orders.isEmpty()) {
            Sort sort = parseSortOrders(orders);
            query.with(sort);
        }

        // Apply pagination
        PageRequest pageRequest = PageRequest.of(page, size);
        query.with(pageRequest);

        // Execute query
        if (response.equals("COURSE_DETAIL")) {
            List<CourseDetailResponse> courses = mongoTemplate.find(query, Course.class)
                    .stream()
                    .map(courseMapper::toCourseDetailResponse)
                    .collect(Collectors.toList());
            long count = mongoTemplate.count(Query.of(query).limit(-1).skip(-1), Course.class);
            return new PageImpl<>(courses, pageRequest, count);
        }

        List<CourseSnippetResponse> courses = mongoTemplate.find(query, Course.class)
                .stream()
                .map(courseMapper::toCourseSnippetResponse)
                .collect(Collectors.toList());

        // Clone query for count operation to avoid conflict
        Query countQuery = Query.of(query).limit(-1).skip(-1);
        long count = mongoTemplate.count(countQuery, Course.class);

        return new PageImpl<>(courses, pageRequest, count);
    }

    @Override
    public Page<?> advancedSearchCourseRequestBody(int page, int size, FilterDTO filterDTO, String response) {

        Query query = new Query();

        // Add AND filters
        if (filterDTO.filterAnd() != null && !filterDTO.filterAnd().isEmpty()) {
            List<Criteria> andCriteria = parseFilterCriteria(filterDTO.filterAnd());
            query.addCriteria(new Criteria().andOperator(andCriteria.toArray(new Criteria[0])));
        }

        // Add OR filters
        if (filterDTO.filterOr() != null && !filterDTO.filterOr().isEmpty()) {
            List<Criteria> orCriteria = parseFilterCriteria(filterDTO.filterOr());
            query.addCriteria(new Criteria().orOperator(orCriteria.toArray(new Criteria[0])));
        }

        // Add sorting
        if (filterDTO.orders() != null && !filterDTO.orders().isEmpty()) {
            Sort sort = parseSortOrders(filterDTO.orders());
            query.with(sort);
        }

        // Apply pagination
        PageRequest pageRequest = PageRequest.of(page, size);
        query.with(pageRequest);

        // Execute query
        if (response.equals("COURSE_DETAIL")) {
            List<CourseDetailResponse> courses = mongoTemplate.find(query, Course.class)
                    .stream()
                    .map(courseMapper::toCourseDetailResponse)
                    .collect(Collectors.toList());
            long count = mongoTemplate.count(Query.of(query).limit(-1).skip(-1), Course.class);
            return new PageImpl<>(courses, pageRequest, count);
        }

        List<CourseSnippetResponse> courses = mongoTemplate.find(query, Course.class)
                .stream()
                .map(courseMapper::toCourseSnippetResponse)
                .collect(Collectors.toList());

        // Clone query for count operation to avoid conflict
        Query countQuery = Query.of(query).limit(-1).skip(-1);
        long count = mongoTemplate.count(countQuery, Course.class);

        return new PageImpl<>(courses, pageRequest, count);
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

