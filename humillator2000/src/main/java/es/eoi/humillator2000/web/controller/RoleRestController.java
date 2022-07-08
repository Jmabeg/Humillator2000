package es.eoi.humillator2000.web.controller;

import es.eoi.humillator2000.data.entity.Role;
import es.eoi.humillator2000.service.RoleService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/role")
public class RoleRestController {

    private final RoleService roleService;

    public RoleRestController(RoleService roleService) {
        this.roleService = roleService;
    }


    @GetMapping
    public Page<Role> findAll(Pageable pageable){
        return roleService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public Role findById(@PathVariable("id") Integer id){
        return roleService.findById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id")Integer id){
        roleService.deleteById(id);
    }

    @PostMapping
    public Role save(Role role){
        return roleService.save(role);
    }
    @PutMapping
    public Role update(Role role){
        return roleService.update(role);
    }
}
