package pl.lab.znk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.lab.znk.domain.UserToken;

@Repository
public interface UserTokenRepository extends JpaRepository<UserToken, Long> {

    UserToken findByUser_id(Long userId);
}
