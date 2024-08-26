package co.istad.mongodbproject.feature.category;

import co.istad.mongodbproject.domain.Category;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface CategoryRepository extends MongoRepository<Category,String> {

//    void disableCategoryById(String id);

    Optional<Category> findByTitle(String title);


}
