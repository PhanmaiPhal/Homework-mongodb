package co.istad.mongodbproject.feature.category;

import co.istad.mongodbproject.base.BasedMessage;
import co.istad.mongodbproject.feature.category.dto.CategoryCreateRequest;
import co.istad.mongodbproject.feature.category.dto.CategoryRequest;
import co.istad.mongodbproject.feature.category.dto.CategoryResponse;
import co.istad.mongodbproject.feature.category.dto.CategoryUpdateRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public  List<CategoryResponse> getAllCategories(){
        return categoryService.getAllCategories();
    }

    @PostMapping
    public void createCategory(@Valid @RequestBody CategoryCreateRequest categoryRequest){
        categoryService.crateCategory(categoryRequest);
    }

    @GetMapping("/{id}")
    public CategoryResponse getCategoryById(@PathVariable String id){
        return categoryService.getCategoryById(id);
    }

    @PutMapping("/{id}")
    public CategoryResponse updateCategory(@PathVariable String id, @RequestBody CategoryUpdateRequest categoryUpdateRequest){
        return categoryService.updateCategory(id, categoryUpdateRequest);
    }

    @DeleteMapping("/{id}")
    public void deleteCategoryById(@PathVariable String id){
        categoryService.deleteCategoryById(id);
    }

    @PutMapping("/{id}/disable")
    public BasedMessage disableCategory(@PathVariable String id){
        return categoryService.disableCategory(id);
    }

    @PutMapping("/{id}/enable")
    public BasedMessage enableCategory(@PathVariable String id){
        return categoryService.enableCategory(id);
    }


}

