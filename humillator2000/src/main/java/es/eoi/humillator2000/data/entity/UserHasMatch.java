package es.eoi.humillator2000.data.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "user_has_match")
public class UserHasMatch {
    @EmbeddedId
    private UserHasMatchId id;

    private Boolean isOwner;
    private Integer mark;

    // Match User Group ID??

    @ManyToOne
    @JoinColumn(name = "match_ID")
    @MapsId("matchId")
    private Match match;

    @ManyToOne
    @JoinColumn(name = "user_ID")
    @MapsId("userId")
    private User user;

    @OneToMany(mappedBy = "userHasMatch")
    private Set<Notification> notifications;



}