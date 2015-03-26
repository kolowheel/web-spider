package webSpider.model.ui.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import webSpider.model.ui.UIManager;
import webSpider.model.pojo.URLStatus;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Yriy on 10/19/14.
 */
public class UIController implements Initializable {

    @FXML
    private TextField url;
    @FXML
    private TextField text;
    @FXML
    private TextField numberOfThreads;
    @FXML
    private TextField maxThreads;
    @FXML
    private Label errorMessage;

    @FXML
    private TableView<URLStatus> urlTable;
    @FXML
    private TableColumn<URLStatus, String> statusURL;
    @FXML
    private TableColumn<URLStatus, String> status;

    private UIManager uiManager;
    private ObservableList<URLStatus> urlsStatus = FXCollections.observableArrayList();

    private boolean checkURLValidity() {
        String link = url.getText().trim().toLowerCase();
        if ((!link.startsWith("http://") &&  !link.startsWith("https://") )
                || link.contains(" ") || (link.length() < 10 ) ) {
            errorMessage.setText("Incorrect URL");
            return false;
        }

        if (text.getText().trim() == "") {
            errorMessage.setText("Searched text is empty");
            return false;
        }

        try {
            Integer.valueOf(numberOfThreads.getText().trim());
        } catch (NumberFormatException e) {
            errorMessage.setText("Incorrect entered the number of threads");
            return false;
        }

        try {
            Integer.valueOf(maxThreads.getText().trim());
        } catch (NumberFormatException e) {
            errorMessage.setText("Incorrectly entered maximum number of visited pages");
            return false;
        }

        return true;
    }

    public void startAction(ActionEvent actionEvent) {

        if (!checkURLValidity())
            return;

        if (errorMessage.getText() != "") {
            errorMessage.setText("");
        }

        uiManager.startSearchingText(url.getText().trim(),text.getText().trim(),
                Integer.valueOf(numberOfThreads.getText().trim()),
                Integer.valueOf(maxThreads.getText().trim()));
    }

    public void stopAction(ActionEvent actionEvent) {
        uiManager.getController().stopSearching();
    }

    public void clearAction(ActionEvent actionEvent) {
        urlsStatus.clear();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        statusURL.setCellValueFactory(cellData -> cellData.getValue().urlProperty());
        status.setCellValueFactory(cellData -> cellData.getValue().statusProperty());

        urlTable.setItems(urlsStatus);
    }


    public void setUIManager(UIManager mainApp) {
        this.uiManager = mainApp;
    }

    public ObservableList getURLStatusTable() {
        return urlsStatus;
    }
}
