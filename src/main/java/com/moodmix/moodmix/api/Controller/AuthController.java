package com.moodmix.moodmix.api.Controller;

import com.moodmix.moodmix.api.DTO.LoginRequest;
import com.moodmix.moodmix.api.DTO.RegisterRequest;
import com.moodmix.moodmix.api.DTO.AuthResponse;
import com.moodmix.moodmix.logic.UserService;
import com.moodmix.moodmix.repository.entities.User;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public AuthResponse register(@Valid @RequestBody RegisterRequest RegisterDTO){
        User CreatedUser = userService.register(RegisterDTO.getUsername(), RegisterDTO.getEmail(), RegisterDTO.getPassword());

       return new AuthResponse(
               CreatedUser.getId(),
               CreatedUser.getUsername(),
               CreatedUser.getEmail(),
               CreatedUser.getCreatedAt()
       );
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public AuthResponse login(@Valid @RequestBody LoginRequest loginDTO) {
        User loggedUser = userService.login(
                loginDTO.getUsernameOrEmail(),
                loginDTO.getPassword()
        );

        return new AuthResponse(
                loggedUser.getId(),
                loggedUser.getUsername(),
                loggedUser.getEmail(),
                loggedUser.getCreatedAt()
        );
    }

}
