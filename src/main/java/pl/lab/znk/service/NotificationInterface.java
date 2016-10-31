package pl.lab.znk.service;

import pl.lab.znk.domain.Notification;
import pl.lab.znk.domain.User;

import java.util.Collection;

public interface NotificationInterface {

    void notifyUsers(Collection<User> userCollection, Notification notification);

    void notifyUser(User user, Notification notification);

}
