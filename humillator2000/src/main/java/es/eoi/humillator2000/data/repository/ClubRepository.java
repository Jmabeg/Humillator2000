package es.eoi.humillator2000.data.repository;

import es.eoi.humillator2000.data.entity.Club;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClubRepository extends JpaRepository<Club, Integer> {
    //List<Club> findByUserHashClubsUserId(Integer id);
    List<Club> findByUserHashClubsUserId(Integer id);
}
