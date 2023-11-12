package com.api.crud.models;



import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.objdetect.CascadeClassifier;
import org.springframework.stereotype.Component;


@Component
public class Detector {

    private void tieneRostro(String imagePath) {
        if(containsFace(imagePath)){
            System.out.println("La imagen contiene un rostro");
        }else{
            System.out.println("No contiene rostro");
        }
    }


    public boolean containsFace(String imagePath) {

        nu.pattern.OpenCV.loadLocally();

        System.out.println(Core.VERSION);

        Mat image = Imgcodecs.imread(imagePath);
        CascadeClassifier faceDetector = new CascadeClassifier("Clasificador/haarcascade_frontalface_default.xml");

        MatOfRect faceDetections = new MatOfRect();
        faceDetector.detectMultiScale(image, faceDetections);

        return faceDetections.toArray().length > 0;
    }

}
