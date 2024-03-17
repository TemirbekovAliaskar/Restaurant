package java12.api;

import jakarta.validation.Valid;
import java12.dto.request.SignInRequest;
import java12.dto.request.UpdateUserRequest;
import java12.dto.request.UserRequestChef;
import java12.dto.request.UserRequestWaiter;
import java12.dto.response.FindUserResponse;
import java12.dto.response.GetAllUserResponse;
import java12.dto.response.SimpleResponse;
import java12.dto.response.UserResponse;
import java12.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserAPI {
    private final UserService userService;


    @GetMapping
    public SimpleResponse signIn(@RequestBody SignInRequest sign){
       return userService.signIn(sign);
    }
    @PostMapping
    public SimpleResponse save(@RequestBody @Valid UserRequestChef userRequestChef){
        return userService.save(userRequestChef);
    }

    @PostMapping("/save/waiter")
    public SimpleResponse saveWaiter(@RequestBody @Valid UserRequestWaiter userRequestWaiter){
       return userService.saveWaiter(userRequestWaiter);
    }


    @PutMapping("/{restaurantId}/{userId}")
    public UserResponse asSign(@PathVariable Long restaurantId, @PathVariable Long userId){
        return userService.asSign(restaurantId,userId);
    }

    @PostMapping("/update/{userId}")
    public UserResponse update(@PathVariable Long userId, @RequestBody @Valid UpdateUserRequest userRequest){
        return userService.update(userId,userRequest);
    }

    @PostMapping("/delete/{userId}")
    public UserResponse delete(@PathVariable Long userId){
        return userService.delete(userId);
    }

    @GetMapping("/all")
    public GetAllUserResponse getAll(){
        return userService.getAll();
    }




}
