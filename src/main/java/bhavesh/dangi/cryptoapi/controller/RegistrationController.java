package bhavesh.dangi.cryptoapi.controller;

import bhavesh.dangi.cryptoapi.dto.User;
import bhavesh.dangi.cryptoapi.service.UserService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/v1")
@AllArgsConstructor
@OpenAPIDefinition(
        info = @Info(
                title = "User API",
                version = "v1",
                description = "User Registration API")
)
public class RegistrationController {

    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody User user) {

        log.debug("Register user called for email : {}", user.getEmail());

        userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
