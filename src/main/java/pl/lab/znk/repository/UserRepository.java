package pl.lab.znk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.lab.znk.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findById(Long id);

    User findByLogin(String login);

    User findByEmail(String email);
}
