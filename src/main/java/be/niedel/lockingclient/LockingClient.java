package be.niedel.lockingclient;

import be.niedel.locking.User;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static java.util.Arrays.stream;
import static java.util.Objects.requireNonNull;

@Component
public class LockingClient {

    private final RestTemplate restTemplate;
    private static final String BASE_URL = "http://localhost:8088/users";

    public LockingClient() {
        this.restTemplate = new RestTemplate();
    }

    public boolean putUser(Long id) {
        try {
            restTemplate.put(BASE_URL + "/" + id, null);
            return true;
        } catch (RestClientException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public List<User> findAll() {
        return stream(requireNonNull(restTemplate.getForObject(BASE_URL, User[].class))).toList();
    }
}
