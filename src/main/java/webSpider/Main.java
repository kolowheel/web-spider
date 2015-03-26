package webSpider;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import webSpider.model.factory.TaskFactory;
import webSpider.model.factory.TaskFactoryByUrl;
import webSpider.model.taskManager.TaskManager;
import webSpider.model.ui.UIManager;

import java.io.IOException;

/**
 * Created by Yriy on 10/19/14.
 */
public class Main {

    static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) throws IOException {

        logger.info("Start project...");
        TaskManager taskManager = new TaskManager();
        TaskFactory factory = new TaskFactoryByUrl(taskManager);
        taskManager.setTaskFactory(factory);

        UIManager ui = new UIManager();

        WebSpiderController controller = new WebSpiderController(ui, taskManager);
        ui.startApplication();

        logger.info("End project...");
    }
}
