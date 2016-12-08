package pl.lab.znk.service;

import pl.lab.znk.domain.Notification;
import pl.lab.znk.domain.User;
import pl.lab.znk.domain.UserToken;

import java.util.Collection;

public interface NotificationInterface {

    void notifyUsers(Collection<User> userCollection, Notification notification);

    void notifyUser(User user, Notification notification);

    void storeToken(User user, UserToken userToken);

    void storeToken(User user, String token);
}
