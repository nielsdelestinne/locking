package be.niedel.locking;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(final UserService userService) {
        this.userService = userService;
    }

    @PutMapping("{id}")
    void updateUserStatus(@PathVariable Long id) {
        userService.updateUser(id);
    }

    @GetMapping()
    List<User> findAll() {
        return userService.findAll();
    }

}
