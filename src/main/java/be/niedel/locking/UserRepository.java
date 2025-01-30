package be.niedel.locking;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import static jakarta.persistence.LockModeType.PESSIMISTIC_WRITE;

public interface UserRepository extends JpaRepository<User, Long> {

    @Lock(PESSIMISTIC_WRITE)
    @Query("SELECT u from lockuser u where u.id = :id")
    User getUser(Long id);

}
