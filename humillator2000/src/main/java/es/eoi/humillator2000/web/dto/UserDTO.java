package es.eoi.humillator2000.web.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;
@Getter
@Setter
public class UserDTO {
    private Integer id;
    private String login;

    //Transformar a blob??
    private byte[] avatar;
    private String email;

    // DEVUELVE DTOs no ENTIDAD
    private Set<RoleDTO> roles;
}
