package webSpider.model.factory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import webSpider.model.pojo.URLStatus;
import webSpider.model.task.Task;
import webSpider.model.taskManager.TaskManager;

import java.util.List;

/**
 * Created by Yriy on 10/19/14.
 */
public class TaskFactoryByUrl implements TaskFactory {

    private TaskManager taskManager;
    static final Logger logger = LogManager.getLogger();

    public TaskFactoryByUrl(TaskManager taskManager){
        this.taskManager = taskManager;
    }

    @Override
    public Task createTask(URLStatus status) {
        logger.info("create new task: "+status.getUrl());
        return new Task(taskManager, status, taskManager.getText());
    }
}
