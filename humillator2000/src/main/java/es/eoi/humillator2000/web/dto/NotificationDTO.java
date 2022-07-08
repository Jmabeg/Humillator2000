package es.eoi.humillator2000.web.dto;

import es.eoi.humillator2000.data.entity.UserHasMatch;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificationDTO {
    private Integer id;
    private Integer minutes;
    private Boolean isNotifyed;

    // ENTERO CON USERID y MATCHID
    private Integer userId;

    private Integer matchId;


}
