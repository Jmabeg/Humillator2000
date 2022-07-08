package es.eoi.humillator2000.data.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Notification implements IEntity<Integer>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // Como hago para que notifique?? hilo en ejecucion - libreria de avisos?
    @Column(nullable = false)
    private Integer minutes;

    private Boolean isNotifyed;

    @ManyToOne
    private UserHasMatch userHasMatch;


}
