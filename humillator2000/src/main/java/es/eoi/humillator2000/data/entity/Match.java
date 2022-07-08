package es.eoi.humillator2000.data.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Getter
@Setter
public class Match implements IEntity<Integer> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private LocalDateTime startingDate;

    @Column(nullable = false)
    private LocalDateTime endingDate;

    private String description;

    @Column(nullable = false)
    private String title;

    private String location;

    private Double price;

    @Column(nullable = false)
    private Boolean delete;

    @ManyToOne
    private Season season;

    @ManyToOne
    private Club club;

    @OneToMany(mappedBy = "match")
    private Set<UserHasMatch> userHasMatches;


}
