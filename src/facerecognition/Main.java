package facerecognition;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.opencv.core.Core;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class Main extends Application {
    File selectedFile;
    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }
    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        String fxmlPath = "src/facerecognition/facerecognition.fxml";
        FileInputStream fxmlFIStream = new FileInputStream(fxmlPath);
        Pane main = (Pane) loader.load(fxmlFIStream);
        Scene scene = new Scene(main);
        //Camera ucam = new PerspectiveCamera(true);
        //scene.setCamera(ucam);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Face Recognition");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
