package com.moodmix.moodmix.api.Controller;

import com.moodmix.moodmix.api.DTO.LoginRequest;
import com.moodmix.moodmix.api.DTO.RegisterRequest;
import com.moodmix.moodmix.api.DTO.UserResponse;
import com.moodmix.moodmix.logic.UserService;
import com.moodmix.moodmix.repository.entities.User;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse register(@Valid @RequestBody RegisterRequest RegisterDTO){
        User CreatedUser = userService.register(RegisterDTO.getUsername(), RegisterDTO.getEmail(), RegisterDTO.getPassword());

       return new UserResponse(
               CreatedUser.getId(),
               CreatedUser.getUsername(),
               CreatedUser.getEmail(),
               CreatedUser.getCreatedAt()
       );
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public UserResponse login(@Valid @RequestBody LoginRequest loginDTO) {
        User loggedUser = userService.login(
                loginDTO.getUsernameOrEmail(),
                loginDTO.getPassword()
        );

        return new UserResponse(
                loggedUser.getId(),
                loggedUser.getUsername(),
                loggedUser.getEmail(),
                loggedUser.getCreatedAt()
        );
    }

}
