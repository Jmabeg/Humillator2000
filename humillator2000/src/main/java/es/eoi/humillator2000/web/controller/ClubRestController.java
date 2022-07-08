package es.eoi.humillator2000.web.controller;

import es.eoi.humillator2000.data.entity.Club;
import es.eoi.humillator2000.service.ClubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/club")
public class ClubRestController {
    private final ClubService clubService;

    @Autowired
    public ClubRestController(ClubService clubService) {
        this.clubService = clubService;
    }


    @PostMapping("/{id}/user")
    public void addUser(@PathVariable("id") Integer id, Integer userId){}

}
