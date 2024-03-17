package java12.repository;

import java12.dto.response.MenuResponse;
import java12.dto.response.MenuSearchResponse;
import java12.entity.MenuItem;
import java12.exception.NotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {
     default MenuItem getByIds(Long menuId){
         return findById(menuId).orElseThrow(() -> new NotFoundException(" Нет такой id " + menuId));
     }
    @Query("SELECT new java12.dto.response.MenuResponse(m.name, m.price, m.description, m.isVegetarian) FROM MenuItem m")
    List<MenuResponse> getAll();

    @Query("SELECT new java12.dto.response.MenuResponse(m.name, m.price, m.description, m.isVegetarian) FROM MenuItem m where m.id =:menuId")
    MenuResponse findByIds(Long menuId);

    @Query("SELECT new java12.dto.response.MenuSearchResponse(s, m.name, m.price, m.description, m.isVegetarian) FROM MenuItem m JOIN m.subcategories s WHERE LOWER(m.name) LIKE LOWER(concat('%', :word, '%')) ORDER BY m.name")
    List<MenuSearchResponse> searchAll(String word);
    @Query("SELECT new java12.dto.response.MenuSearchResponse(s, m.name, m.price, m.description, m.isVegetarian) FROM MenuItem m JOIN m.subcategories s ORDER BY CASE WHEN :word = 'asc' THEN m.price END ASC, CASE WHEN :word = 'desc' THEN m.price END DESC")
    List<MenuSearchResponse> sort(String word);
    @Query("select m from  MenuItem m")
    List<MenuItem> getAllFilter();

    @Query("select m from MenuItem m where m.id in (:menu)")
    List<MenuItem> getAllMenuId(List<Long> menu);

    @Query("SELECT new java12.dto.response.MenuResponse(m.name, m.price, m.description, m.isVegetarian)" +
            " FROM MenuItem m join  m.restaurant r join r.users u join u.cheques c where  c.id =:checkId")
     List<MenuResponse> checkById(Long checkId);
}