package java12.service.impl;

import jakarta.transaction.Transactional;
import java12.dto.request.MenuRequest;
import java12.dto.response.DefaultResponse;
import java12.dto.response.MenuResponse;
import java12.dto.response.MenuSearchResponse;
import java12.entity.MenuItem;
import java12.entity.Restaurant;
import java12.entity.Subcategory;
import java12.repository.MenuItemRepository;
import java12.repository.RestaurantRepository;
import java12.repository.SubcategoryRepository;
import java12.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {

    private final RestaurantRepository restaurantRepository;
    private final MenuItemRepository menuItemRepository;
    private final SubcategoryRepository subcategoryRepository;
    @Override
    public DefaultResponse save(Long restId,Long subId, MenuRequest menuRequest) {
        Restaurant restaurant = restaurantRepository.getByID(restId);
        Subcategory subcategory = subcategoryRepository.getByIds(subId);
        MenuItem menuItem = new MenuItem();
        menuItem.setName(menuRequest.name());
        menuItem.setDescription(menuRequest.description());
        menuItem.setPrice(menuRequest.price());
        menuItem.setVegetarian(menuRequest.isVegetarian());
        menuItemRepository.save(menuItem);
        menuItem.setRestaurant(restaurant);
        menuItem.setSubcategories(subcategory);
        menuItemRepository.save(menuItem);

        return DefaultResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message(" Успешно сохранен меню !")
                .build();
    }

    @Override @Transactional
    public DefaultResponse update(Long menuId, MenuRequest menuRequest) {
        MenuItem menuItem = menuItemRepository.getByIds(menuId);
        menuItem.setName(menuRequest.name());
        menuItem.setDescription(menuRequest.description());
        menuItem.setPrice(menuRequest.price());
        menuItem.setVegetarian(menuRequest.isVegetarian());
        return DefaultResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message(" Успешно изменен меню !")
                .build();
    }

    @Override
    public List<MenuResponse> getAll() {
        return menuItemRepository.getAll();
    }

    @Override
    public MenuResponse findById(Long menuId) {
        return menuItemRepository.findByIds(menuId);
    }

    @Override
    public DefaultResponse delete(Long menuId) {
       menuItemRepository.delete(menuItemRepository.getByIds(menuId));
        return DefaultResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message(" Успешно удален меню !")
                .build();
    }

    @Override
    public List<MenuSearchResponse> search(String word) {
        return menuItemRepository.searchAll(word);
    }

    @Override
    public List<MenuSearchResponse> sort(String word) {
        return menuItemRepository.sort(word) ;
    }

    public List<MenuSearchResponse> filter(boolean words) {
        List<MenuItem> menuItems = menuItemRepository.getAllFilter();
        List<MenuSearchResponse> menuSearchResponses = new ArrayList<>();

        for (MenuItem menuItem : menuItems) {
            if (menuItem.getSubcategories() != null && menuItem.isVegetarian() == words) {
                menuSearchResponses.add(new MenuSearchResponse(menuItem.getSubcategories(), menuItem.getName(), menuItem.getPrice(), menuItem.getDescription(), menuItem.isVegetarian()));
            }
        }

        return menuSearchResponses;
    }


}
