package facerecognition;

import facerecognition.utils.Utils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;

import java.io.File;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class Controller {
    @FXML
    private TextField fpsCounter = new TextField();
    @FXML
    private Button button;
    @FXML
    private ImageView usercam;
    private boolean isActive = false;
    private boolean shouldOverlay = false;
    private VideoCapture uCam = new VideoCapture();
    private ScheduledExecutorService timer;
    private String filePath;
    FileChooser fileChooser = new FileChooser();
    @FXML
    private CheckBox imgoverlaycheckbox = new CheckBox();
    @FXML
    protected void imgoverlay(javafx.event.ActionEvent actionEvent) {
        if (shouldOverlay) {
            shouldOverlay = false;
        } else {
            shouldOverlay = true;
        }
    }
    @FXML
    protected void startcam(javafx.event.ActionEvent actionEvent) {
        if (shouldOverlay) {
            FileChooser.ExtensionFilter imageFilter = new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png");
            fileChooser.getExtensionFilters().add(imageFilter);
            File selectedFile = fileChooser.showOpenDialog(null);
            filePath = selectedFile.getPath();
        } else {
            filePath = "null";
        }
        if (!isActive) {
            uCam.open(0);
            if (uCam.isOpened()){
                this.isActive = true;
                Runnable getFrames = new Runnable() {
                    @Override
                    public void run() {
                        Mat frame = new Mat();
                        try {
                            uCam.read(frame);
                        } catch (Exception c) {
                            System.err.println(c);
                        }
                        Image img = Utils.mat2Image(FacialRecog.main(frame, filePath)); //frame
                        Utils.onFXThread(usercam.imageProperty(), img);
                    }
                };
                this.timer = Executors.newSingleThreadScheduledExecutor();
                this.timer.scheduleAtFixedRate(getFrames, 0, 33, TimeUnit.MILLISECONDS);
            }
        }
    }


}
