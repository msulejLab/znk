package pl.lab.znk.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.ZonedDateTime;

@Entity
public class Consultation {

    @Id
    @GeneratedValue
    @Column(name = "consultation_id")
    private Long id;

    @Column(name = "place")
    private String place;

    @Column(name = "time")
    private ZonedDateTime time;

    @Column(name = "teacher_name")
    private String teacherName;

    protected Consultation(){};

    public Consultation(String place, ZonedDateTime time, String teacherName) {
        this.place = place;
        this.time = time;
        this.teacherName = teacherName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public ZonedDateTime getTime() {
        return time;
    }

    public void setTime(ZonedDateTime time) {
        this.time = time;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }
}
