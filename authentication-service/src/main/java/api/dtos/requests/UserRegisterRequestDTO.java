package api.dtos.requests;

import api.contracts.IPasswordUtils;
import api.models.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserRegisterRequestDTO {

    @NotNull(message = "É necessário informar um nome.")
    @Size(min = 5, max = 90, message = "O nome deve conter entre {min} e {max} caracteres.")
    private String name;

    @NotNull(message = "É necessário informar um email.")
    @Size(min = 10, max = 120, message = "O email deve conter entre {min} e {max} caracteres.")
    @Email(message = "O email informado é inválido.")
    private String email;

    @NotNull(message = "É necessário informar uma senha.")
    @Size(min = 6, max = 100, message = "A senha deve conter entre {min} e {max} caracteres.")
    private String password;

    public User convert() {
        return User
                .builder()
                .name(this.getName())
                .email(this.getEmail())
                .password(IPasswordUtils.encrypt(this.getPassword()))
                .build();
    }
}
