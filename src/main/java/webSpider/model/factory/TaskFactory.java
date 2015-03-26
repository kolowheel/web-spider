package webSpider.model.factory;

import webSpider.model.pojo.URLStatus;
import webSpider.model.task.Task;

import java.util.List;

/**
 * Created by Yriy on 10/19/14.
 */
public interface TaskFactory {

    public Task createTask(URLStatus status);
}
