package java12.api;

import java12.dto.request.Subcategoryrequest;
import java12.dto.response.DefaultResponse;
import java12.dto.response.SubcategoryCategoryResponse;
import java12.dto.response.Subcategoryresponse;
import java12.service.CategoryService;
import java12.service.SubcategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/sub")
public class SubcategoryAPI {
    private final SubcategoryService subcategoryService;

    @PostMapping("/{catId}")
    public DefaultResponse save(@PathVariable Long catId,@RequestBody Subcategoryrequest subcategoryrequest){
        return subcategoryService.save(catId,subcategoryrequest);
    }

    @GetMapping("/{subId}")
    public DefaultResponse update(@PathVariable Long subId,@RequestBody Subcategoryrequest subcategoryrequest){
        return subcategoryService.update(subId,subcategoryrequest);
    }

    @GetMapping("/find/{subId}")
    public Subcategoryresponse findById(@PathVariable Long subId){
        return subcategoryService.findById(subId);
    }

    @GetMapping("/all/{catId}")
    public List<Subcategoryresponse> findAll(@PathVariable Long catId){
        return subcategoryService.findAll(catId);
    }

    @PostMapping("/delete/{subId}")
    public DefaultResponse delete(@PathVariable Long subId){
        return subcategoryService.delete(subId);
    }


    @GetMapping("/all")
    public List<SubcategoryCategoryResponse> allCategory(){
        return subcategoryService.getAllCategory();
    }


}
