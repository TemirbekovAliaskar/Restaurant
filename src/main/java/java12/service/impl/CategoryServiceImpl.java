package java12.service.impl;

import jakarta.transaction.Transactional;
import java12.dto.request.CategoryRequest;
import java12.dto.response.CategoryResponse;
import java12.dto.response.DefaultResponse;
import java12.entity.Category;
import java12.exception.NotFoundException;
import java12.repository.CategoryRepository;
import java12.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    @Override
    public DefaultResponse save(CategoryRequest categoryRequest) {
        Category category = new Category();
        category.setName(categoryRequest.name());
        categoryRepository.save(category);
        return DefaultResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message(" Удачно сохранен категория !!!")
                .build();
    }

    @Override
    public List<CategoryResponse> findAll() {
        try {
            List<Category> all = categoryRepository.findAll();
            List<CategoryResponse> categoryResponses = new ArrayList<>();
            for (Category category : all) {
                categoryResponses.add(new CategoryResponse(category.getId(), category.getName()));
            }
            return categoryResponses;
        }catch (NotFoundException e){
            throw new NotFoundException(e.getMessage());
        }
    }

    @Override
    public CategoryResponse findById(Long catId) {
        Category category = categoryRepository.getByIds(catId);
        CategoryResponse categoryResponse = new CategoryResponse(category.getId(), category.getName());
        return categoryResponse;
    }

    @Override @Transactional
    public DefaultResponse update(Long catId, CategoryRequest categoryRequest) {
        Category category = categoryRepository.getByIds(catId);
        category.setName(categoryRequest.name());
        return DefaultResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message(" Удачно изменен категории !!!")
                .build();
    }

    @Override
    public DefaultResponse delete(Long catId) {
        Category category = categoryRepository.getByIds(catId);
        categoryRepository.delete(category);
        return DefaultResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message(" Удачно удален категории !!!")
                .build();
    }
}
