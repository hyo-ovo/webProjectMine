package MINE.service;

import MINE.domain.User;
import MINE.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void signUp(String username, String rawPassword) {
        if (userRepository.findByUsername(username).isPresent()) {
            // ❗예외 타입을 IllegalArgumentException으로 변경
            throw new IllegalArgumentException("이미 존재하는 사용자입니다.");
        }

        String encodedPassword = passwordEncoder.encode(rawPassword);
        User user = new User(username, encodedPassword);
        userRepository.save(user);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public void register(String username, String rawPassword) {
        signUp(username, rawPassword);
    }
}
