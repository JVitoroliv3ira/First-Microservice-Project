package api.services;

import api.contracts.ICrudService;
import api.models.User;
import api.repositories.UserRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService implements ICrudService<User, Long, UserRepository> {
    @Getter
    private final UserRepository repository;

    @Override
    public String getEntityNotFoundMessage() {
        return "Não foi possível encontrar o usuário requisitado.";
    }
}
