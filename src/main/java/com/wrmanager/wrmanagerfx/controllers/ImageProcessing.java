package com.wrmanager.wrmanagerfx.controllers;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class ImageProcessing {


    public Mat processFrame(Mat img) {
        Imgproc.resize(img, img, new Size(700, 480));
        MatOfByte m = new MatOfByte();
        Imgcodecs.imencode(".jpg", img, m);
        byte[] byteArray = m.toArray();
        BufferedImage bufImage = null;
        try {
            InputStream in = new ByteArrayInputStream(byteArray);
            bufImage = ImageIO.read(in);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Mat processedMat = new Mat();
        assert bufImage != null;
        processedMat = bufferedImageToMat(bufImage);
        return processedMat;
    }

    public Image matToImage(Mat mat) {
        return SwingFXUtils.toFXImage(matToBufferedImage(mat), null);
    }

    private BufferedImage matToBufferedImage(Mat matrix) {
        int cols = matrix.cols();
        int rows = matrix.rows();
        int elemSize = (int) matrix.elemSize();
        byte[] data = new byte[cols * rows * elemSize];
        int type;
        matrix.get(0, 0, data);

        switch (matrix.channels()) {
            case 1:
                type = BufferedImage.TYPE_BYTE_GRAY;
                break;
            case 3:
                type = BufferedImage.TYPE_3BYTE_BGR;
                // bgr to rgb
                byte b;
                for (int i = 0; i < data.length; i = i + 3) {
                    b = data[i];
                    data[i] = data[i + 2];
                    data[i + 2] = b;
                }
                break;
            default:
                return null;
        }

        BufferedImage image2 = new BufferedImage(cols, rows, type);
        image2.getRaster().setDataElements(0, 0, cols, rows, data);

        return image2;
    }

    private Mat bufferedImageToMat(BufferedImage image) {
        Mat mat = new Mat(image.getHeight(), image.getWidth(), CvType.CV_8UC3);
        byte[] data = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        mat.put(0, 0, data);
        return mat;
    }

    public static Path saveCapturedImage(Mat img) {
        Imgproc.resize(img, img, new Size(700, 480));
        MatOfByte m = new MatOfByte();
        Imgcodecs.imencode(".jpg", img, m);
        byte[] byteArray = m.toArray();
        Path filePath = null;
        try {
            // Get the current working directory
            String workingDir = System.getProperty("user.dir");

            // Define the relative path and filename
            String relativePath = "images/captured_image.jpg"; // Modify the path and filename as needed

            // Create the absolute path
            filePath = Path.of(workingDir, relativePath);

            // Write the image to the specified path
            Files.write(filePath, byteArray, StandardOpenOption.CREATE);
            System.out.println("Image saved: " + filePath);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return filePath;
    }

    public static void executePythonFile(String  scriptPath, String imagePath) {
        try {
            // Start the Python process
            ProcessBuilder processBuilder = new ProcessBuilder("python", scriptPath, imagePath);
            Process process = processBuilder.start();
            // Read the output of the Python process
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            StringBuilder output = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }

            // Wait for the process to finish
            int exitCode = process.waitFor();

            // Print the output and exit code
            System.out.println("Output:\n" + output.toString());
            System.out.println("Exit Code: " + exitCode);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }


}















