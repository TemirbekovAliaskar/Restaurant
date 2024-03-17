package java12.api;

import java12.dto.response.AllRestaurants;
import java12.dto.request.RestaurantRequest;
import java12.dto.response.DefaultResponse;
import java12.dto.response.RestaurantResponse;
import java12.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/rest")
public class RestaurantAPI {
    private final RestaurantService restaurantService;

    @PostMapping
    public DefaultResponse save (@RequestBody RestaurantRequest request){
        return restaurantService.save(request);
    }

    @PostMapping("/update/{resId}")
    public DefaultResponse update(@PathVariable Long resId,@RequestBody RestaurantRequest request){
        return restaurantService.update(resId,request);
    }

    @PostMapping("/delete/{resId}")
    public DefaultResponse delete(@PathVariable Long resId){
        return restaurantService.delete(resId);
    }

    @GetMapping("/all")
    public List<AllRestaurants> all(){
        return restaurantService.allRestaurants();
    }

    @GetMapping("/find/{id}")
    public RestaurantResponse findById(@PathVariable Long id){
        return restaurantService.findById(id);
    }



}
