package java12.api;

import jakarta.validation.Valid;
import java12.dto.request.MenuRequest;
import java12.dto.response.DefaultResponse;
import java12.dto.response.MenuResponse;
import java12.dto.response.MenuSearchResponse;
import java12.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/menu")
public class MenuItemAPI {

    private final MenuService menuService;

    @PostMapping("/save/{restId}/{subId}")
    public DefaultResponse save(@PathVariable Long restId,@PathVariable Long subId, @RequestBody @Valid MenuRequest menuRequest){
        return menuService.save(restId,subId,menuRequest);
    }

    @PostMapping("/update/{menuId}")
    public DefaultResponse update(@PathVariable Long menuId,@RequestBody MenuRequest menuRequest){
        return menuService.update(menuId,menuRequest);
    }

    @GetMapping ("/all")
    public List<MenuResponse> getAll(){
        return menuService.getAll();
    }

    @GetMapping("/find/{menuId}")
    public MenuResponse findById(@PathVariable Long menuId){
        return menuService.findById(menuId);
    }
    @PostMapping("/delete/{menuId}")
    public DefaultResponse delete(@PathVariable Long menuId){
        return menuService.delete(menuId);
    }

    @GetMapping("/search")
    public List <MenuSearchResponse> search(@RequestParam String word){
        return menuService.search(word);
    }

    @GetMapping("/sort")
    public List <MenuSearchResponse> sort(@RequestParam String word){
        return menuService.sort(word);
    }

    @GetMapping("/filter")
    public List <MenuSearchResponse> sort(@RequestParam boolean words){
        return menuService.filter(words);
    }





}
