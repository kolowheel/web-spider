package webSpider;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import webSpider.model.taskManager.TaskManager;
import webSpider.model.ui.UIManager;
import webSpider.model.pojo.URLStatus;

/**
 * Created by Yriy on 10/19/14.
 */
public class WebSpiderController {

    private UIManager uiManager;
    private TaskManager taskManager;
    static final Logger logger = LogManager.getLogger();

    public WebSpiderController(UIManager uiManager, TaskManager taskManager){
        this.taskManager = taskManager;
        this.uiManager = uiManager;
        uiManager.setController(this);
        taskManager.setController(this);
    }

    public void beginSearching(String url, String text, Integer numberOfThreads, Integer maxNumberOfPages){
        logger.info("begin searching text: "+text+". first url: "+url);
        taskManager.initialization();
        taskManager.setText(text);
        taskManager.setMaxNumberOfPages(maxNumberOfPages);
        taskManager.setNumberOfThreads(numberOfThreads);
        taskManager.append(url);
    }

    public void stopSearching(){
        taskManager.shutdownExecutorService();
    }

    public void addNewURLStatus(URLStatus urlStatus){
        uiManager.addURLStatus(urlStatus);
    }

}
