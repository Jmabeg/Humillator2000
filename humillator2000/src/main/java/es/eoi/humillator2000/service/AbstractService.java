package es.eoi.humillator2000.service;


import es.eoi.humillator2000.data.entity.IEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Pageable;
import java.util.List;

public abstract class AbstractService<ID, E extends IEntity<ID>, R extends JpaRepository<E, ID>> {
    private final R repository;

    public AbstractService(R repository) {
        this.repository = repository;
    }

    public Page<E> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public E findById(ID id) {
        return repository.findById(id).orElse(null);
    }

    public void deleteById(ID id) {
        repository.deleteById(id);
    }

    public E save(E e) {
        if (e.getId() != null) {
            throw new RuntimeException("Ya existe");
        }
        return repository.save(e);
    }

    public E update(E e) {
        if (e.getId() == null) {
            throw new RuntimeException("No existe");
        }
        return repository.save(e);
    }

    protected R getRepository() {
        return repository;
    }
}
