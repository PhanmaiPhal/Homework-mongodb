package co.istad.mongodbproject.feature.category;

import co.istad.mongodbproject.base.BasedMessage;
import co.istad.mongodbproject.domain.Category;
import co.istad.mongodbproject.feature.category.dto.CategoryCreateRequest;
import co.istad.mongodbproject.feature.category.dto.CategoryResponse;
import co.istad.mongodbproject.feature.category.dto.CategoryUpdateRequest;
import co.istad.mongodbproject.mapper.CategoryMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public List<CategoryResponse> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream().map(
                categoryMapper::toCategoryResponse
        ).toList();
    }

    @Override
    public void crateCategory(CategoryCreateRequest categoryRequest) {

        Category category = categoryMapper.fromCategoryCreateRequest(categoryRequest);

        categoryRepository.save(category);
    }

    @Override
    public CategoryResponse getCategoryById(String id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Category has not been found!"
                ));

        return categoryMapper.toCategoryResponse(category);
    }

    @Override
    public CategoryResponse updateCategory(String id, CategoryUpdateRequest categoryUpdateRequest) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Category not found!"
                ));

        categoryMapper.updateCategoryFormRequest(category, categoryUpdateRequest);
        categoryRepository.save(category);

        return categoryMapper.toCategoryResponse(category);
    }

    @Override
    public void deleteCategoryById(String id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Category not found!"
                ));

        categoryRepository.delete(category);
    }

    @Override
    public BasedMessage disableCategory(String id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Category not found!"
                ));

        category.setIsDeleted(true);
        categoryRepository.save(category);

        return new BasedMessage("Category has been disabled!");
    }

    @Override
    public BasedMessage enableCategory(String id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Category has not been enabled!"
                ));

        category.setIsDeleted(false);
        categoryRepository.save(category);

        return new BasedMessage("Category has been enabled!");
    }
}
