package pl.lab.znk.service.util;

import com.google.gson.JsonObject;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.lab.znk.domain.Notification;
import pl.lab.znk.domain.User;
import pl.lab.znk.domain.UserToken;
import pl.lab.znk.repository.UserTokenRepository;
import pl.lab.znk.service.NotificationInterface;

import java.io.IOException;
import java.util.Collection;

@Service
public class FirebaseNotificationService implements NotificationInterface {

    private static final Logger log = LoggerFactory.getLogger(FirebaseNotificationService.class);

    private static final String HOST_NAME = "https://fcm.googleapis.com/fcm/send";
    private static final String FIREBASE_SERVER_KEY = "key=AAAAWMmzNeY:APA91bFCnSXHObeJTGryvYW" +
        "qR7Nz-9t4LKIk8LW-NI3N_ddtXanauZkQ0EFh3jO5t0D_gXBXs4iB2NLs_H2E88CpXS5uUcYhJLR3bQZR" +
        "cmThvKMZyXiJ4Ks7ShW1hwp2crHaJTPq-mrvAV6Y6dchgIffVT5tKl6EhQ";

    private UserTokenRepository userTokenRepository;

    @Autowired
    public FirebaseNotificationService(UserTokenRepository userTokenRepository) {
        this.userTokenRepository = userTokenRepository;
    }

    @Override
    public void notifyUsers(Collection<User> userCollection, Notification notification) {
        for (User user : userCollection) {
            notifyUser(user, notification);
        }
    }

    @Override
    public void notifyUser(User user, Notification notification) {
        String userToken = getUserToken(user);
        JsonObject bodyObject = makeRequestObject(notification, userToken);
        doHttpRequest(bodyObject);
    }

    @Override
    public void storeToken(User user, UserToken userToken) {
        UserToken existingToken = userTokenRepository.findByUser_id(user.getId());
        if (existingToken != null) {
            userTokenRepository.delete(existingToken);
        }
        userTokenRepository.save(userToken);
    }

    @Override
    public void storeToken(User user, String token) {
        storeToken(user, new UserToken(user, token));
    }

    private JsonObject makeRequestObject(Notification notification, String userToken) {
        JsonObject bodyObject = makeBody(userToken);
        JsonObject notificationObject = makeNotification(notification);
        bodyObject.add("notification", notificationObject);
        return bodyObject;
    }

    private JsonObject makeNotification(Notification notification) {
        JsonObject notificationObject = new JsonObject();
        notificationObject.addProperty("body", notification.getContent());
        notificationObject.addProperty("title", notification.getTitle());
        return notificationObject;
    }

    private JsonObject makeBody(String userToken){
        JsonObject body = new JsonObject();
        body.addProperty("to", userToken);
        return body;
    }

    private void doHttpRequest(JsonObject bodyObject) {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        try {
            HttpPost request = new HttpPost(HOST_NAME);
            StringEntity params = new StringEntity(bodyObject.toString());
            request.addHeader("Content-Type", "application/json");
            request.addHeader("Authorization", FIREBASE_SERVER_KEY);
            request.setEntity(params);
            httpClient.execute(request);
            httpClient.execute(request, httpResponse -> {
                log.debug(httpResponse.toString());
                return true;
            });
        } catch (Exception e) {
            e.getStackTrace();
        } finally {
            try {
                httpClient.close();
            } catch (IOException ignored) {

            }
        }
    }

    private String getUserToken(User user){
        return userTokenRepository.findByUser_id(user.getId()).getToken();
//        return "eH3mBZNWZ2w:APA91bHN7ILHpseLhkpNT_AJqyY1xkKCWhChg4tCMi2BfUyKdTND8Q0jm59ljup0MaggqI4Nc4iTu9drf62ENbxd9lNllWOotCQbLZeHRn8MA4GDeTix6Io7q0i1YdVjtO5gZHOPynYP";
    }
}
