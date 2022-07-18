package es.eoi.humillator2000.web.dto;

import es.eoi.humillator2000.data.entity.Club;
import es.eoi.humillator2000.data.entity.Match;
import es.eoi.humillator2000.service.mapper.EntityMapper;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;
@Getter
@Setter
public class ClubDTO implements EntityMapper<Integer, Club> {
    private Integer id;
    private String name;





}
