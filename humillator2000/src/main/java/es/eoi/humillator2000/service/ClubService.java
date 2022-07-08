package es.eoi.humillator2000.service;

import es.eoi.humillator2000.data.entity.Club;
import es.eoi.humillator2000.data.entity.User;
import es.eoi.humillator2000.data.entity.UserHasClub;
import es.eoi.humillator2000.data.repository.ClubRepository;
import es.eoi.humillator2000.data.repository.UserHasClubRepository;
import es.eoi.humillator2000.data.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClubService extends AbstractService<Integer, Club, ClubRepository>{

    private final UserRepository userRepository;
    private final UserHasClubRepository userHasClubRepository;

    @Autowired
    public ClubService(ClubRepository repository, UserRepository userRepository, UserHasClubRepository userHasClubRepository) {
        super(repository);
        this.userRepository = userRepository;
        this.userHasClubRepository = userHasClubRepository;
    }

    public List<Club> getClubsByUserId(Integer id){
        return super.getRepository().findByUserHashClubsUserId(id);
    }


    public void addUser(Integer id, Integer userId){
        final User user = this.userRepository.findById(userId).orElseThrow(()-> new RuntimeException("No existe usuario para el id + ID----------"));
        final Club club = this.getRepository().findById(id).orElseThrow(()-> new RuntimeException("No ID CLUB"));

        final UserHasClub userHasClub = new UserHasClub();
        userHasClub.setUser(user);
        userHasClub.setClub(club);

        this.userHasClubRepository.save(userHasClub);
    }




}
