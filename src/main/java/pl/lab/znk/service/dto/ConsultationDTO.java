package pl.lab.znk.service.dto;

import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the Consultation entity.
 */
public class ConsultationDTO implements Serializable {

    private Long id;

    private ZonedDateTime dateTime;

    private Boolean cancelled;


    private Long teacherId;
    

    private String teacherLogin;

    private Set<UserDTO> registeredStudents = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public ZonedDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(ZonedDateTime dateTime) {
        this.dateTime = dateTime;
    }
    public Boolean getCancelled() {
        return cancelled;
    }

    public void setCancelled(Boolean cancelled) {
        this.cancelled = cancelled;
    }

    public Long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Long userId) {
        this.teacherId = userId;
    }


    public String getTeacherLogin() {
        return teacherLogin;
    }

    public void setTeacherLogin(String userLogin) {
        this.teacherLogin = userLogin;
    }

    public Set<UserDTO> getRegisteredStudents() {
        return registeredStudents;
    }

    public void setRegisteredStudents(Set<UserDTO> users) {
        this.registeredStudents = users;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ConsultationDTO consultationDTO = (ConsultationDTO) o;

        if ( ! Objects.equals(id, consultationDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ConsultationDTO{" +
            "id=" + id +
            ", dateTime='" + dateTime + "'" +
            ", cancelled='" + cancelled + "'" +
            '}';
    }
}
