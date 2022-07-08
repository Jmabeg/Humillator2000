package es.eoi.humillator2000.web.controller;


import es.eoi.humillator2000.data.entity.IEntity;
import es.eoi.humillator2000.service.AbstractService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.*;

public abstract class AbstractRestController <S extends AbstractService<ID, E, R>, E extends IEntity<ID>, ID, R extends JpaRepository <E, ID>> {

    private final S service;


    public AbstractRestController(S service) {
        this.service = service;
    }

    @GetMapping
    public Page<E> findAll(Pageable pageable){ return this.service.findAll(pageable); }

    @GetMapping("/{id}")
    public E findById(@PathVariable("id") ID id) { return this.service.findById(id); }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") ID id) { this.service.deleteById(id); }

    @PostMapping
    public E save(E e){
        return this.service.save(e);
    }

    @PutMapping
    public E update(ID id, E e) { return this.service.update(e); }


}
