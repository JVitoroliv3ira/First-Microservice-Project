package api.services;

import api.models.User;
import api.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.assertj.core.api.Java6Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserServiceTest {

    private final static String ENTITY_NOT_FOUND_MESSAGE = "Não foi possível encontrar o usuário requisitado.";
    @Mock
    private final UserRepository userRepository;
    private UserService userService;

    @Autowired
    public UserServiceTest(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @BeforeEach
    void setUp() {
        this.userService = new UserService(this.userRepository);
    }

    private User buildUserPayload(Long id, String name, String email, String password) {
        return User
                .builder()
                .id(id).name(name).email(email).password(password)
                .build();
    }

    @Test
    void create_should_call_create_method_of_user_repository() {
        User payload = this.buildUserPayload(null, "test test", "test@test.com", "test_test");
        this.userService.create(payload);
        verify(this.userRepository, times(1)).save(payload);
    }

    @Test
    void create_should_set_entity_id_to_null_before_call_create_method_of_user_repository() {
        User payload = this.buildUserPayload(1L, "test test", "test@test.com", "test_test");
        this.userService.create(payload);
        verify(this.userRepository, times(1))
                .save(this.buildUserPayload(null, "test test", "test@test.com", "test_test"));
    }

    @Test
    void read_should_call_find_by_id_method_of_user_repository() {
        User payload = this.buildUserPayload(1L, "test test", "test@test.com", "test_test");
        when(this.userRepository.findById(payload.getId())).thenReturn(Optional.of(payload));
        this.userService.read(payload.getId());
        verify(this.userRepository, times(1)).findById(payload.getId());
    }

    @Test
    void read_should_throw_an_exception_when_the_requested_user_does_not_exists() {
        Long payload = 1L;
        when(this.userRepository.findById(payload)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> this.userService.read(payload))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining(ENTITY_NOT_FOUND_MESSAGE);
    }

    @Test
    void update_should_throw_an_exception_when_the_requested_user_does_not_exists() {
        User payload = this.buildUserPayload(1L, "test test", "test@test.com", "test_test");
        when(this.userRepository.existsById(payload.getId())).thenReturn(Boolean.FALSE);
        assertThatThrownBy(() -> this.userService.update(payload))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining(ENTITY_NOT_FOUND_MESSAGE);
    }

    @Test
    void update_should_call_exists_by_id_method_of_user_repository() {
        User payload = this.buildUserPayload(1L, "test test", "test@test.com", "test_test");
        when(this.userRepository.existsById(payload.getId())).thenReturn(Boolean.TRUE);
        this.userService.update(payload);
        verify(this.userRepository, times(1)).existsById(payload.getId());
    }

    @Test
    void update_should_call_save_method_of_user_repository() {
        User payload = this.buildUserPayload(1L, "test test", "test@test.com", "test_test");
        when(this.userRepository.existsById(payload.getId())).thenReturn(Boolean.TRUE);
        this.userService.update(payload);
        verify(this.userRepository, times(1)).save(payload);
    }

    @Test
    void delete_should_call_find_by_id_method_of_user_repository() {
        User payload = this.buildUserPayload(1L, "test test", "test@test.com", "test_test");
        when(this.userRepository.findById(payload.getId())).thenReturn(Optional.of(payload));
        this.userService.delete(payload.getId());
        verify(this.userRepository, times(1)).findById(payload.getId());
    }

    @Test
    void delete_should_call_delete_method_of_user_repository() {
        User payload = this.buildUserPayload(1L, "test test", "test@test.com", "test_test");
        when(this.userRepository.findById(payload.getId())).thenReturn(Optional.of(payload));
        this.userService.delete(payload.getId());
        verify(this.userRepository, times(1)).delete(payload);
    }

    @Test
    void delete_should_throw_an_exception_when_the_requested_user_does_not_exists() {
        Long payload = 1L;
        when(this.userRepository.findById(payload)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> this.userService.delete(payload))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining(ENTITY_NOT_FOUND_MESSAGE);
    }
}