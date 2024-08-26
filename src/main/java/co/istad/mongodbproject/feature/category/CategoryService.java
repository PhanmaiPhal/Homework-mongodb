package co.istad.mongodbproject.feature.category;


import co.istad.mongodbproject.base.BasedMessage;
import co.istad.mongodbproject.domain.Category;
import co.istad.mongodbproject.feature.category.dto.*;

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

    List<CategoryPopularDTO> getPopularCategories();

}
