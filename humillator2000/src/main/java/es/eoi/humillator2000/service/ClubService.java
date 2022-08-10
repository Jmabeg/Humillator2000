package es.eoi.humillator2000.service;

import es.eoi.humillator2000.data.entity.*;
import es.eoi.humillator2000.data.repository.*;
import es.eoi.humillator2000.service.mapper.ClubMapper;
import es.eoi.humillator2000.web.dto.ClubDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClubService extends AbstractService<Integer, Club, ClubRepository, ClubDTO, ClubMapper>{

    private final UserRepository userRepository;
    private final UserHasClubRepository userHasClubRepository;
    private final ClubRoleRepository clubRoleRepository;

    public ClubService(ClubRepository repository, ClubMapper mapper, UserRepository userRepository, UserHasClubRepository userHasClubRepository, ClubRoleRepository clubRoleRepository) {
        super(repository, mapper);
        this.userRepository = userRepository;
        this.userHasClubRepository = userHasClubRepository;
        this.clubRoleRepository = clubRoleRepository;
    }


    public List<Club> getClubsByUserId(Integer id){
        return super.getRepository().findByUserUserHasClubsUserId(id);
    }


    public UserHasClub addUser(Integer id, Integer userId, Integer roleId){
        final User user = this.userRepository.findById(userId).orElseThrow(()-> new RuntimeException("No existe usuario para el id + ID----------"));
        final Club club = this.getRepository().findById(id).orElseThrow(()-> new RuntimeException("No ID CLUB"));
        final ClubRole clubRole = this.clubRoleRepository.findById(roleId).orElseThrow(()->new RuntimeException("No existe el rol"));

        final UserHasClub userHasClub = new UserHasClub();
        userHasClub.setUser(user);
        userHasClub.setClub(club);
        userHasClub.setClubRole(clubRole);

        return this.userHasClubRepository.save(userHasClub);
    }
    
    public void deleteUser (Integer id, Integer userId){
        final User user = this.userRepository.findById(userId).orElseThrow(()-> new RuntimeException("No existe usuario para el id + ID----------"));
        final Club club = this.getRepository().findById(id).orElseThrow(()-> new RuntimeException("No ID CLUB"));

        final UserHasClub userHasClub = new UserHasClub();
        userHasClub.setUser(user);
        userHasClub.setClub(club);

        this.userHasClubRepository.delete(userHasClub);
    }
}
