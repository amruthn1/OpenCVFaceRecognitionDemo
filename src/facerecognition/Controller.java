package facerecognition;

import facerecognition.utils.Utils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
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
    private Button button;
    @FXML
    private ImageView usercam;
    private boolean isActive = false;
    private VideoCapture uCam = new VideoCapture();
    private ScheduledExecutorService timer;
    FileChooser fileChooser = new FileChooser();
    @FXML
    protected void startcam(javafx.event.ActionEvent actionEvent) {
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
                        Image img = Utils.mat2Image(FacialRecog.main(frame)); //frame
                        Utils.onFXThread(usercam.imageProperty(), img);
                    }
                };
                this.timer = Executors.newSingleThreadScheduledExecutor();
                this.timer.scheduleAtFixedRate(getFrames, 0, 33, TimeUnit.MILLISECONDS);

            }
        }
    }


}
