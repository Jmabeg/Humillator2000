package es.eoi.humillator2000.data.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
public class Season implements IEntity<Integer> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private LocalDateTime startingDate;

    @Column(nullable = false)
    private LocalDateTime endingDate;

    @OneToMany(mappedBy = "season")
    private Set<Match> matches;

}