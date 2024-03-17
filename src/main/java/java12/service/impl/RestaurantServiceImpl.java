package java12.service.impl;

import jakarta.transaction.Transactional;
import java12.dto.request.RestaurantRequest;
import java12.dto.response.AllRestaurants;
import java12.dto.response.DefaultResponse;
import java12.dto.response.RestaurantResponse;
import java12.entity.Restaurant;
import java12.exception.NotFoundException;
import java12.repository.RestaurantRepository;
import java12.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {
private final RestaurantRepository restaurantRepository;

    @Override
    public DefaultResponse save(RestaurantRequest request) {
        Restaurant restaurant = new Restaurant();
        restaurant.setName(request.name());
        restaurant.setLocation(request.location());
        restaurant.setRestType(request.restType());
        restaurant.setServicePercent(request.servicePercent());
        restaurantRepository.save(restaurant);
        restaurant.setNumberOfEmployees(restaurant.getUsers().size());

        return DefaultResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Удачно сохранен ресторан! ")
                .build();
    }

    @Override @Transactional
    public DefaultResponse update(Long resId, RestaurantRequest request) {
        Restaurant restaurant = restaurantRepository.getByID(resId);
        restaurant.setName(request.name());
        restaurant.setLocation(request.location());
        restaurant.setRestType(request.restType());
        restaurant.setServicePercent(request.servicePercent());
        return DefaultResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Удачно изменен ресторан! ")
                .build();
    }

    @Override
    public DefaultResponse delete(Long resId) {
        Restaurant restaurant = restaurantRepository.getByID(resId);
        restaurantRepository.delete(restaurant);
        restaurantRepository.deleteUser(resId);
        return DefaultResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Удачно удален ресторан! ")
                .build();
    }

    @Override
    public List<AllRestaurants>allRestaurants() {
        try {
            List<Restaurant> restaurants = restaurantRepository.findAll();
            List<AllRestaurants> allRestaurants = new ArrayList<>();

            for (Restaurant restaurant : restaurants) {
                allRestaurants.add(new AllRestaurants(restaurant.getId(), restaurant.getName(), restaurant.getLocation(),restaurant.getRestType(), restaurant.getServicePercent(), restaurant.getNumberOfEmployees()));
            }
            return allRestaurants;
        }catch (NotFoundException e){
            throw new NotFoundException(e.getMessage());
        }
    }

    @Override
    public RestaurantResponse findById(Long id) {
        Restaurant restaurant = restaurantRepository.getByID(id);
        RestaurantResponse response = new RestaurantResponse();
        response.setId(restaurant.getId());
        response.setName(restaurant.getName());
        response.setRestType(restaurant.getRestType());
        response.setServicePercent(restaurant.getServicePercent());
        response.setNumberOfEmployees(restaurant.getNumberOfEmployees());

        return response;
    }
}
