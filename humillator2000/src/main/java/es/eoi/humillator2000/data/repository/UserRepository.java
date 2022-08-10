package es.eoi.humillator2000.data.repository;

import es.eoi.humillator2000.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByLogin(String login);
}
