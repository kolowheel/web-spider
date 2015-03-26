package webSpider.model.task;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import webSpider.model.pojo.URLStatus;
import webSpider.model.taskManager.TaskAppender;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.UnknownHostException;

/**
 * Created by Yriy on 10/18/14.
 */
public class Task implements Runnable {

    private TaskAppender taskAppender;
    private URLStatus url;
    private String text;
    static final Logger logger = LogManager.getLogger();
    private static final String userAgent = "Mozilla/5.0 (Windows NT 6.1; WOW64) " +
            "AppleWebKit/535.2 (KHTML, like Gecko) Chrome/33.0.1750.152 Safari/535.2";

    public Task(TaskAppender taskAppender, URLStatus status, String text){
        this.taskAppender = taskAppender;
        this.url = status;
        this.text = text;
    }

    @Override
    public void run() {
        String response = null;
        String errorMessage = null;
        try {
            response = action().toString();
        } catch (UnknownHostException e) {
            logger.warn(Thread.currentThread().getName(), e);
            errorMessage = " (Can not connect to the page)";
        } catch (IOException e) {
            logger.warn(Thread.currentThread().getName(), e);
            errorMessage = " (Unable to download page)";
        }

        if (errorMessage != null) {
            response = StatusResponse.ERROR.toString().concat(errorMessage);
        }
        url.setStatus(response);
    }

    private StatusResponse action() throws IOException {
        Connection conn = Jsoup.connect(url.getUrl());
        Document doc;

        doc = conn.timeout(5000).userAgent(userAgent).get();

        Elements links = doc.select("a[href]");
        for (Element link : links) {
            String linToLowerCase = link.attr("href").toLowerCase();
            if (linToLowerCase.startsWith("http://") || linToLowerCase.startsWith("https://")) {
                taskAppender.append(link.attr("href"));
            }
        }

        if (doc.text().toLowerCase().contains(text.toLowerCase()))
            return StatusResponse.FOUND;
        else
            return StatusResponse.NOTFOUND;
    }
}
