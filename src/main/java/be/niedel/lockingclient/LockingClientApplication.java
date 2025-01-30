package be.niedel.lockingclient;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@SpringBootApplication(exclude = {org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration.class})
public class LockingClientApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(LockingClientApplication.class);
        app.setDefaultProperties(Collections.singletonMap("server.port", "8089"));
        app.run(args);
    }

    @Override
    public void run(String... args) {
        var lockingClient = new LockingClient();


        // Execute 3 concurrent putUser invocations
        List<CompletableFuture<Boolean>> futures = List.of(
                CompletableFuture.supplyAsync(() -> lockingClient.putUser(1L)),
                CompletableFuture.supplyAsync(() -> lockingClient.putUser(1L)),
                CompletableFuture.supplyAsync(() -> lockingClient.putUser(1L)),
                CompletableFuture.supplyAsync(() -> lockingClient.putUser(1L))
        );

        // Wait for all tasks to complete
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();

        System.out.println("\nDB state after requests");
        System.out.println("=============================");
        lockingClient.findAll().forEach(System.out::println);
        System.out.println();

        System.exit(1);
    }

}
