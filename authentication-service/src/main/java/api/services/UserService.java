package api.services;

import api.contracts.ICrudService;
import api.models.User;
import api.repositories.UserRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@RequiredArgsConstructor
@Service
public class UserService implements ICrudService<User, Long, UserRepository> {
    private final static String EMAIL_IN_USE_MESSAGE = "O email informado já está sendo utilizado por outra conta.";
    @Getter
    private final UserRepository repository;

    public void uniqueEmailCheck(String email) {
        if (Boolean.TRUE.equals(this.checkEmailExists(email))) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, EMAIL_IN_USE_MESSAGE);
        }
    }

    private Boolean checkEmailExists(String email) {
        return this.repository.existsByEmail(email);
    }

    @Override
    public String getEntityNotFoundMessage() {
        return "Não foi possível encontrar o usuário requisitado.";
    }
}
