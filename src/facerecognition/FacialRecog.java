package facerecognition;

import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.objdetect.Objdetect;

public class FacialRecog {
    protected int absoluteFaceSize = 0;
    private CascadeClassifier faceCascade = new CascadeClassifier("src/facerecognition/model.xml");
    MatOfRect faces = new MatOfRect();
    public static Mat main(Mat frame) {
        FacialRecog fr = new FacialRecog();
        return fr.process(frame);
    }
    private Mat process(Mat frame){
        Mat output = new Mat();
        Mat overlay = Imgcodecs.imread("src/facerecognition/pig.png");
        Mat grayFrame = new Mat();
        Imgproc.cvtColor(frame, grayFrame, Imgproc.COLOR_BGR2GRAY);
        Imgproc.equalizeHist(grayFrame, grayFrame);
        if (this.absoluteFaceSize == 0){
            int height = grayFrame.rows();
            if (Math.round(height * 0.2f) > 0) {
                this.absoluteFaceSize = Math.round(height * 0.2f);
            }
        }
        this.faceCascade.detectMultiScale(grayFrame, this.faces, 1.1, 2, 0 | Objdetect.CASCADE_SCALE_IMAGE, new Size(this.absoluteFaceSize, this.absoluteFaceSize), new Size());
        Rect[] facesArr = this.faces.toArray();
        Rect rect;
        for (int j = 0; j < facesArr.length; j++) {
            Imgproc.rectangle(frame, facesArr[j].tl(), facesArr[j].br(), new Scalar(0, 0, 255), 3);
            //Overlay Image Code
            rect = new Rect(facesArr[j].tl(), facesArr[j].br());
            Imgproc.resize(overlay, overlay, rect.size());
            Mat submat = frame.submat(new Rect(rect.x, rect.y, overlay.cols(), overlay.rows()));
            overlay.copyTo(submat);
        }
        return frame;
    }
}
