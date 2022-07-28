package es.eoi.humillator2000.web.controller;

import es.eoi.humillator2000.data.entity.Club;
import es.eoi.humillator2000.service.ClubService;
import es.eoi.humillator2000.web.dto.ClubDTO;
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

    @GetMapping
    public Page<ClubDTO> findAll(Pageable pageable){
        return clubService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public ClubDTO findById(@PathVariable("id") Integer id){
        return clubService.findById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id")Integer id){
        clubService.deleteById(id);
    }

    @PostMapping
    public ClubDTO save(ClubDTO clubDTO){
        return clubService.save(clubDTO);
    }

    @PostMapping("/{id}/user")
    public void addUser(@PathVariable("id") Integer id, Integer userId){}






}
