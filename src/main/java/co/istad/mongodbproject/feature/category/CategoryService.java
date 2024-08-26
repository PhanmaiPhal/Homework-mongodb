package co.istad.mongodbproject.feature.category;


import co.istad.mongodbproject.base.BasedMessage;
import co.istad.mongodbproject.domain.Category;
import co.istad.mongodbproject.feature.category.dto.CategoryCreateRequest;
import co.istad.mongodbproject.feature.category.dto.CategoryRequest;
import co.istad.mongodbproject.feature.category.dto.CategoryResponse;
import co.istad.mongodbproject.feature.category.dto.CategoryUpdateRequest;

import java.util.List;
import java.util.Optional;

public interface CategoryService {

    List<CategoryResponse> getAllCategories();

    void crateCategory(CategoryCreateRequest categoryCreateRequest);

    CategoryResponse getCategoryById(String id);

    CategoryResponse updateCategory(String id, CategoryUpdateRequest categoryUpdateRequest);

    void deleteCategoryById(String id);


    BasedMessage disableCategory(String id);

    BasedMessage enableCategory(String id);

}
