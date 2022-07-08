package es.eoi.humillator2000.web.controller;

import es.eoi.humillator2000.data.entity.Season;
import es.eoi.humillator2000.service.SeasonService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/season")
public class SeasonRestController {

    private final SeasonService seasonService;


    public SeasonRestController(SeasonService seasonService) {
        this.seasonService = seasonService;
    }

    @GetMapping
    public Page<Season> findAll(Pageable pageable){
        return seasonService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public Season findById(@PathVariable("id") Integer id){
        return seasonService.findById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id")Integer id){
        seasonService.deleteById(id);
    }

    @PostMapping
    public Season save(Season season){
        return seasonService.save(season);
    }
    @PutMapping
    public Season update(Season season){
            return seasonService.update(season);
    }
}
