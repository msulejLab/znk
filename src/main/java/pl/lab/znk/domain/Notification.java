package pl.lab.znk.domain;

public final class Notification {

    private final String title;
    private final String content;

    public Notification(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }
}
