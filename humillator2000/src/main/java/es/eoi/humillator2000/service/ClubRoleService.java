package es.eoi.humillator2000.service;

import es.eoi.humillator2000.data.entity.ClubRole;
import es.eoi.humillator2000.data.repository.ClubRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ClubRoleService extends AbstractService<Integer, ClubRole, ClubRoleRepository> {

    @Autowired
    public ClubRoleService(ClubRoleRepository repository) {
        super(repository);
    }



}
