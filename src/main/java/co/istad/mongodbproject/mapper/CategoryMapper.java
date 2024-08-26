package co.istad.mongodbproject.mapper;

import co.istad.mongodbproject.domain.Category;
import co.istad.mongodbproject.feature.category.dto.CategoryCreateRequest;
import co.istad.mongodbproject.feature.category.dto.CategoryResponse;
import co.istad.mongodbproject.feature.category.dto.CategoryUpdateRequest;
import org.mapstruct.*;


@Mapper(componentModel = "spring")
public interface CategoryMapper {

    Category fromCategoryCreateRequest(CategoryCreateRequest categoryRequest);


    CategoryResponse toCategoryResponse(Category category);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateCategoryFormRequest(@MappingTarget Category category, CategoryUpdateRequest categoryUpdateRequest);



}
