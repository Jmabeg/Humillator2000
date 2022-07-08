package es.eoi.humillator2000.data.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@Setter
public class UserHasClubId implements Serializable {
    private static final long serialVersionUID = 2742452792947774020L;
    private Integer userId;
    private Integer clubId;
    private Integer roleId;



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserHasClubId)) return false;
        UserHasClubId that = (UserHasClubId) o;
        return Objects.equals(getUserId(), that.getUserId()) && Objects.equals(getClubId(), that.getClubId()) && Objects.equals(getRoleId(), that.getRoleId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUserId(), getClubId(), getRoleId());
    }
}
