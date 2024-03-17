package java12.repository;

import java12.dto.response.SubcategoryCategoryResponse;
import java12.dto.response.Subcategoryresponse;
import java12.entity.Subcategory;
import java12.exception.NotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubcategoryRepository extends JpaRepository<Subcategory, Long> {

    @Query("select s from Subcategory s join s.category sc where sc.id =:catId")
    List<Subcategory> findAllId(Long catId);
    default Subcategory getByIds(Long subId){
        return findById(subId).orElseThrow(() -> new NotFoundException(" Нет такой подкатегории id "  + subId));
    }

    @Query("select new java12.dto.response.Subcategoryresponse(s.id,s.name) from Subcategory s join s.category sc where sc.id =:catId order by s.name")
    List<Subcategoryresponse> getAllId(Long catId);


    @Query("select new java12.dto.response.SubcategoryCategoryResponse(s.category,s.id,s.name) from Subcategory s join s.category sc order by sc.name")
    List<SubcategoryCategoryResponse> getAllCateg();
}