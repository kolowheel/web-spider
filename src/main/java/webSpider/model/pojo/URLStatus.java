package webSpider.model.pojo;

import javafx.beans.property.*;

/**
 * Created by Yriy on 10/19/14.
 */
public class URLStatus {

    private final StringProperty statusURL;
    private final StringProperty status;


    public URLStatus() {
        this(null, null);
    }

    public URLStatus(String statusURL, String status) {
        this.statusURL = new SimpleStringProperty(statusURL);
        this.status = new SimpleStringProperty(status);
    }

    public String getUrl() {
        return statusURL.get();
    }

    public void setUrl(String url) {
        this.statusURL.set(url);
    }

    public StringProperty urlProperty() {
        return statusURL;
    }

    public String getStatus() {
        return status.get();
    }

    public void setStatus(String status) {
        this.status.set(status);
    }

    public StringProperty statusProperty() {
        return status;
    }

}
