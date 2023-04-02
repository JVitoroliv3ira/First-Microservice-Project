package api.controllers.v1;

import api.dtos.requests.UserRegisterRequestDTO;
import api.dtos.responses.ResponseDTO;
import api.models.User;
import api.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/auth")
@RestController
public class AuthController {

    private final UserService userService;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @PostMapping(path = "/register")
    public ResponseEntity<ResponseDTO<User>> register(@RequestBody @Valid UserRegisterRequestDTO request) {
        try {
            this.userService.uniqueEmailCheck(request.getEmail());
            User result = this.userService.create(request.convert());
            this.kafkaTemplate.send("greetings-email", request.getEmail());
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(new ResponseDTO<>(result, null));
        } catch (ResponseStatusException ex) {
            return ResponseEntity
                    .status(ex.getStatusCode())
                    .body(new ResponseDTO<>(null, List.of(ex.getMessage())));
        }
    }
}
