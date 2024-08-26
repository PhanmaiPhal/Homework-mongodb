package co.istad.mongodbproject.feature.coure;

import co.istad.mongodbproject.domain.Course;
import co.istad.mongodbproject.feature.coure.dto.CourseVisibility;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;


public interface CourseRepository extends MongoRepository<Course, String> {

    Page<Course> findAllByIsDeletedIsFalse(PageRequest pageRequest);

    Optional<Course> findAllBySlug(String slug);

    Optional<Course> findAllByInstructorName(String instructorUserName);

    Optional<Course> findByStatus(CourseVisibility courseVisibility);

    List<Course> findByPrice(double price);

}
