package pl.lab.znk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.lab.znk.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {

    User findById(Long id);

    User findByLogin(String login);

    User findByEmail(String email);
}
