package com.api.crud.models;



import com.api.crud.repositories.ClasificadorRepository;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.MatOfRect;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.objdetect.CascadeClassifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;


@Component
public class Detector {

    @Autowired
    ClasificadorRepository clasificadorRepository;


    private void tieneRostro(String imagePath) {
        if(containsFace(imagePath)){
            System.out.println("La imagen contiene un rostro");
        }else{
            System.out.println("No contiene rostro");
        }
    }


    public byte[] containsFaceByte(byte[] imageBytes) {

        nu.pattern.OpenCV.loadLocally();

        System.out.println(Core.VERSION);

        // Convertir el array de bytes a un Mat
        MatOfByte matOfByte = new MatOfByte(imageBytes);
        Mat image = Imgcodecs.imdecode(matOfByte, Imgcodecs.IMREAD_UNCHANGED);

        CascadeClassifier faceDetector = new CascadeClassifier("clasificador/haarcascade_frontalface_default.xml");



        MatOfRect faceDetections = new MatOfRect();
        faceDetector.detectMultiScale(image, faceDetections);

        return (faceDetections.toArray().length > 0) ? imageBytes : null;
    }








    public boolean containsFace(String imagePath) {

        nu.pattern.OpenCV.loadLocally();

        System.out.println(Core.VERSION);

        Mat image = Imgcodecs.imread(imagePath);
        //CascadeClassifier faceDetector = new CascadeClassifier("app/src/main/resources/imagenes/haarcascade_frontalface_default.xml");
        CascadeClassifier faceDetector = new CascadeClassifier("app/clasificador/haarcascade_frontalface_default.xml");
        //CascadeClassifier faceDetector = new CascadeClassifier("haarcascade_frontalface_default.xml");

        MatOfRect faceDetections = new MatOfRect();
        faceDetector.detectMultiScale(image, faceDetections);

        return faceDetections.toArray().length > 0;
    }


    public String archivoExiste(String ruta){

        return Files.exists(Path.of(ruta)) ? "Si existe" : "No existe";
    }

}
