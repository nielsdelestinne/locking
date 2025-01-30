package be.niedel.locking;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public void updateUser(Long id) {
        System.out.println(LocalDateTime.now() + " - Start \t\t\t\t thread: " + Thread.currentThread().getName());

        var user = userRepository.getUser(id);

        System.out.println(LocalDateTime.now() + " - Continued \t\t\t thread: " + Thread.currentThread().getName());

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        if(!user.isStatus()) {
            user.setStatus(true);
        } else {
            System.out.println(LocalDateTime.now() + " - Change detected \t thread: " + Thread.currentThread().getName());
        }

        userRepository.save(user);
        System.out.println(LocalDateTime.now() + " - Ended \t\t\t\t thread: " + Thread.currentThread().getName());
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }
}

