package pl.lab.znk.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Consultation.
 */
@Entity
@Table(name = "consultation")
public class Consultation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "date_time")
    private ZonedDateTime dateTime;

    @Column(name = "cancelled")
    private Boolean cancelled;

    @ManyToOne
    private User teacher;

    @ManyToMany
    @JoinTable(name = "consultation_registered_students",
               joinColumns = @JoinColumn(name="consultations_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="registered_students_id", referencedColumnName="ID"))
    private Set<User> registeredStudents = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getDateTime() {
        return dateTime;
    }

    public Consultation dateTime(ZonedDateTime dateTime) {
        this.dateTime = dateTime;
        return this;
    }

    public void setDateTime(ZonedDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Boolean isCancelled() {
        return cancelled;
    }

    public Consultation cancelled(Boolean cancelled) {
        this.cancelled = cancelled;
        return this;
    }

    public void setCancelled(Boolean cancelled) {
        this.cancelled = cancelled;
    }

    public User getTeacher() {
        return teacher;
    }

    public Consultation teacher(User user) {
        this.teacher = user;
        return this;
    }

    public void setTeacher(User user) {
        this.teacher = user;
    }

    public Set<User> getRegisteredStudents() {
        return registeredStudents;
    }

    public Consultation registeredStudents(Set<User> users) {
        this.registeredStudents = users;
        return this;
    }

    public Consultation addRegisteredStudents(User user) {
        registeredStudents.add(user);
        return this;
    }

    public Consultation removeRegisteredStudents(User user) {
        registeredStudents.remove(user);
        return this;
    }

    public void setRegisteredStudents(Set<User> users) {
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
        Consultation consultation = (Consultation) o;
        if(consultation.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, consultation.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Consultation{" +
            "id=" + id +
            ", dateTime='" + dateTime + "'" +
            ", cancelled='" + cancelled + "'" +
            '}';
    }
}
