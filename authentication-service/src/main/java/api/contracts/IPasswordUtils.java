package api.contracts;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public interface IPasswordUtils {
    static PasswordEncoder getEncoder() {
        return new BCryptPasswordEncoder();
    }

    static String encrypt(String password) {
        return getEncoder().encode(password);
    }

    static Boolean matches(String rawPassword, String encodedPassword) {
        return getEncoder().matches(rawPassword, encodedPassword);
    }
}
