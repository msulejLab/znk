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

    private String description;

    private String dateTime;

    private Boolean cancelled;

    private Long teacherId;

    private String teacherLogin;

    private String teacherName;

    private Set<UserDTO> registeredStudents = new HashSet<>();

    private String address;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
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

    public void setRegisteredStudents(Set<UserDTO> registeredStudents) {
        this.registeredStudents = registeredStudents;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    @Override
    public String toString() {
        return "ConsultationDTO{" +
            "id=" + id +
            ", description='" + description + '\'' +
            ", dateTime='" + dateTime + '\'' +
            ", cancelled=" + cancelled +
            ", teacherId=" + teacherId +
            ", teacherLogin='" + teacherLogin + '\'' +
            ", teacherName='" + teacherName + '\'' +
            ", registeredStudents=" + registeredStudents +
            ", address='" + address + '\'' +
            '}';
    }
}
