package es.eoi.humillator2000.web.dto;

import es.eoi.humillator2000.data.entity.Club;
import es.eoi.humillator2000.data.entity.Season;
import es.eoi.humillator2000.data.entity.UserHasMatch;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Set;


@Getter
@Setter
public class MatchDTO {
    private Integer id;

    // LO RECIBE EN STRING DE BBDD SE CONVIERTE A FORMATO EN MAPPER
    private String startingDate;
    private String endingDate;

    private String description;
    private String title;
    private String location;
    private Double price;
    private Boolean delete;

    // TRANSFORMAR A ID ->Revisar!!
    private Integer seasonId;
    private Integer clubId;
    private Set<Integer> userIds;




}
