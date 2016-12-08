package pl.lab.znk.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class UserToken {

    @Id
    @GeneratedValue
    @Column(name = "token_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Column(name = "token")
    private String token;

    public UserToken() {}

    public UserToken(User user, String token) {
        this.user = user;
        this.token = token;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserToken userToken = (UserToken) o;
        return Objects.equals(id, userToken.id) &&
            Objects.equals(user, userToken.user) &&
            Objects.equals(token, userToken.token);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, token);
    }

    @Override
    public String toString() {
        return "UserToken{" +
            "id=" + id +
            ", user=" + user +
            ", token='" + token + '\'' +
            '}';
    }
}
