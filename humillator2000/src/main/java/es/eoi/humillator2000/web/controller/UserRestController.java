package es.eoi.humillator2000.web.controller;

import es.eoi.humillator2000.data.entity.User;
import es.eoi.humillator2000.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserRestController {

    private final UserService userService;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public Page<User> findAll(Pageable pageable){
        return userService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public User findById(@PathVariable("id") Integer id){
        return userService.findById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id")Integer id){
        userService.deleteById(id);
    }

    @PostMapping
    public User save(User user){
        return userService.save(user);
    }
    @PutMapping
    public User update(User user){
        return userService.update(user);
    }
}
