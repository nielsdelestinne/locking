package be.niedel.locking;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class LockingApplication {

	public static void main(String[] args) {
		SpringApplication.run(LockingApplication.class, args);
	}
}

@Component
class StartupRunner implements CommandLineRunner {

	private final UserRepository userRepository;

	public StartupRunner(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public void run(String... args) {
		this.userRepository.save(new User(1));
		this.userRepository.save(new User(2));
		this.userRepository.save(new User(3));
		this.userRepository.save(new User(4));
	}
}
