package es.eoi.humillator2000.data.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class UserHasClub {

    @EmbeddedId
    private UserHasClubId id;

    private Float rating;

    @ManyToOne
    @JoinColumn(name = "club_ID")
    @MapsId("clubId")
    private Club club;

    @ManyToOne
    @JoinColumn(name = "user_ID")
    @MapsId("userId")
    private User user;

    @ManyToOne
    @JoinColumn(name = "club_role_ID")
    @MapsId("roleId")
    private ClubRole clubRole;




}
