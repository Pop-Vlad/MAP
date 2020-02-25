package Gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader programsWindowLoader = new FXMLLoader(getClass().getResource("SelectionWindow.fxml"));
        Parent programsWindow = programsWindowLoader.load();
        primaryStage.setTitle("Programs");
        primaryStage.setScene(new Scene(programsWindow));

        FXMLLoader runWindowLoader = new FXMLLoader(getClass().getResource("RunWindow.fxml"));
        Parent runWindow = runWindowLoader.load();
        SelectionController swc = programsWindowLoader.getController();
        swc.setRunController(runWindowLoader.getController());
        Stage runWindowStage = new Stage();
        runWindowStage.setTitle("Run");
        runWindowStage.setScene(new Scene(runWindow));

        runWindowStage.show();
        primaryStage.show();
    }

}
