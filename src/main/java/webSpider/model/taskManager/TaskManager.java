package webSpider.model.taskManager;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import webSpider.WebSpiderController;
import webSpider.model.factory.TaskFactory;
import webSpider.model.pojo.URLStatus;
import webSpider.model.task.StatusResponse;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Yriy on 10/18/14.
 */
public class TaskManager implements TaskAppender {

    private ExecutorService executor;
    private TaskFactory taskFactory;
    private String text;
    private Set<String> visitedURLs;
    private AtomicInteger numberOfPages;
    private WebSpiderController controller;

    static final Logger logger = LogManager.getLogger();

    public TaskManager() {
    }

    public void initialization() {
        if (executor != null && !executor.isShutdown())
            executor.shutdown();
        this.visitedURLs = new HashSet<>();

    }

    public void setText(String text) {
        this.text = text;
    }

    public void setNumberOfThreads(int numberOfThreads) {
        this.executor = Executors.newFixedThreadPool(numberOfThreads);
    }

    public void setMaxNumberOfPages(int numberOfPages) {
        this.numberOfPages = new AtomicInteger(numberOfPages);
    }

    public void setTaskFactory(TaskFactory taskFctory) {
        this.taskFactory = taskFctory;
    }

    @Override
    public void append(String url) {
        if (visitedURLs.contains(url))
            return;

        if (!visitedURLs.add(url))
            return;

        if (numberOfPages.decrementAndGet() >= 0) {
            URLStatus status = new URLStatus(url, StatusResponse.PROCESSING.toString());
            controller.addNewURLStatus(status);
            try {
                if ( !executor.isShutdown())
                    executor.execute(taskFactory.createTask(status));
            } catch (RejectedExecutionException e) {
                logger.warn("Executor service is shurdown",e);
                status.setStatus(StatusResponse.NOTFOUND.toString());
            }
        } else if (!executor.isShutdown()) {
            executor.shutdown();
        }
    }

    public void shutdownExecutorService() {
        if (executor != null && !executor.isShutdown()){
            executor.shutdown();
            logger.info("Executor service shutdown");
        }
    }

    public String getText() {
        return text;
    }

    public void setController(WebSpiderController controller) {
        this.controller = controller;
    }
}
