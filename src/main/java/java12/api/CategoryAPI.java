package java12.api;

import java12.dto.request.CategoryRequest;
import java12.dto.response.CategoryResponse;
import java12.dto.response.DefaultResponse;
import java12.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/category")
public class CategoryAPI {
    private final CategoryService categoryService;

    @PostMapping
    public DefaultResponse save(@RequestBody CategoryRequest categoryRequest){
        return categoryService.save(categoryRequest);
    }

    @GetMapping("/all")
    public List<CategoryResponse> findAll(){
        return categoryService.findAll();
    }

    @GetMapping("/find/{catId}")
    public CategoryResponse findById(@PathVariable Long catId){
        return categoryService.findById(catId);
    }

    @PostMapping("/update/{catId}")
    public DefaultResponse update(@PathVariable Long catId,@RequestBody CategoryRequest categoryRequest){
        return categoryService.update(catId,categoryRequest);
    }

    @PostMapping("/delete/{catId}")
    public DefaultResponse delete(@PathVariable Long catId){
        return categoryService.delete(catId);
    }
}
