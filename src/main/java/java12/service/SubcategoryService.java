package java12.service;

import java12.dto.request.Subcategoryrequest;
import java12.dto.response.DefaultResponse;
import java12.dto.response.SubcategoryCategoryResponse;
import java12.dto.response.Subcategoryresponse;

import java.util.List;

public interface SubcategoryService {
    List<Subcategoryresponse> findAll(Long catId);

    DefaultResponse save(Long catId, Subcategoryrequest subcategoryrequest);

    DefaultResponse update(Long subId, Subcategoryrequest subcategoryrequest);

    Subcategoryresponse findById(Long subId);

    DefaultResponse delete(Long subId);

    List<SubcategoryCategoryResponse> getAllCategory();
}
