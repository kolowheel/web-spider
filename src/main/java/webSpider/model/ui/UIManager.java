package webSpider.model.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import webSpider.WebSpiderController;
import webSpider.model.ui.controllers.UIController;
import webSpider.model.pojo.URLStatus;

import java.io.IOException;


/**
 * Created by Yriy on 10/19/14.
 */

public class UIManager extends Application {

    private static WebSpiderController controller;
    private static UIController uiController;
    static final Logger logger = LogManager.getLogger();

    @Override
    public void start(Stage primaryStage) {

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/form.fxml"));
            final Parent root = loader.load();

            uiController = loader.getController();
            uiController.setUIManager(this);

            primaryStage.setTitle("Web Spider");
            primaryStage.setScene(new Scene(root, root.getBoundsInLocal().getWidth(), root.getLayoutY()));
primaryStage.setResizable(false);
            primaryStage.show();

        } catch (IOException e) {
            logger.error("not found fxml file",e);
        }
    }

    public void startApplication() {
        UIManager.launch();
    }

    public void setController(WebSpiderController controller) {
        this.controller = controller;
    }

    public WebSpiderController getController() {
        return controller;
    }

    public void startSearchingText(String url, String text, Integer numberOfThreads, Integer maxNumberOfPages) {
        controller.beginSearching(url, text, numberOfThreads, maxNumberOfPages);
    }

    public void addURLStatus(URLStatus urlStatus) {
        uiController.getURLStatusTable().add(urlStatus);
    }

}
