package com.java.logdemo;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.sourceforge.tess4j.Tesseract;

@SpringBootApplication
@RestController
public class LogDemoApplication {

    private static final Logger logger = LogManager.getLogger(LogDemoApplication.class);

    public static void main2(String[] args) {
        System.out.println("helsfdk");
    }

    public static void main4(String[] args) {
        SpringApplication.run(LogDemoApplication.class, args);
    }

    @RequestMapping("/")
    String index() {
        logger.debug("This is a debug message");
        logger.info("This is an info message");
        logger.warn("This is a warn message");
        logger.error("This is an error message");
        return "index";
    }

    public static void main(String[] args) {
        try {
            // Load the input image
            // BufferedImage image = ImageIO.read(new File("aadhar_card.jpg"));
            BufferedImage image = ImageIO.read(new File("image.png"));
            String aadharNumber = "548550008000";

        //    maskAadharNumber1(image, aadharNumber);
        maskAadharNumber(image, aadharNumber);
            // BufferedImage image = ImageIO.read(new
            // File("../../../../resources/static/aadhar_card.jpg"));

            // Use Tesseract to perform OCR and extract text from the image
            //  Tesseract tesseract = new Tesseract();
            // // tesseract.setLanguage("eng+hin"); // English and Hindi
            //  tesseract.setDatapath("D:\\Tess4J-3.4.8-src\\Tess4J\\tessdata");
            //  String text = tesseract.doOCR(image);
            // System.out.println("fsdlk: " + tesseract.doOCR(image));
            //  System.out.println("text: " + text);

            // Analyze the extracted text to find the Aadhar number
            // String aadharNumber = extractAadharNumber(text);

            // // If Aadhar number found, mask it in the image
            // if (aadharNumber != null) {
            // // System.out.println("aadhar number: " + aadharNumber);
            // maskAadharNumber(image, aadharNumber);
            // } else {
            // System.out.println("Aadhar number not found in the image.");
            // }

            // Save the modified image
            ImageIO.write(image, "jpg", new File("masked_aadhar_car.jpg"));

            System.out.println("Aadhar card masked successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String extractAadharNumber(String text) {
        // Regular expression to match 12 consecutive digits
        Pattern pattern = Pattern.compile("\\b\\d{12}\\b");
        Matcher matcher = pattern.matcher(text);

        // If a match is found, return the Aadhar number
        if (matcher.find()) {
            return matcher.group();
        } else {
            return null; // No Aadhar number found
        }
    }

    private static void maskAadharNumber(BufferedImage image, String aadharNumber) {
        // Define the color to use for masking (you can choose any color)
        Color maskColor = Color.BLACK;

        // Get the dimensions of the image
        int imageWidth = image.getWidth();
        int imageHeight = image.getHeight();

        // Create a graphics context for drawing on the image
        Graphics2D g2d = image.createGraphics();

        // Set the color for masking
        g2d.setColor(maskColor);
        // X: 7, Y: 12, Width: 400, Height: 238
        // Calculate the coordinates of the region to be masked based on Aadhar number
        // length
        int x = 120; // Starting x-coordinate
        int y = 180; // Starting y-coordinate
        // int width = aadharNumber.length() * 20; // Width of the masked region
        // (assuming each digit's width is 20 pixels)
        int width = 95; // Width of the masked region (assuming each digit's width is 20 pixels)
        int height = 23; // Height of the masked region (adjust as needed)

        // Fill the identified region with the mask color
        g2d.fillRect(x, y, width, height);

        // Dispose of the graphics context
        g2d.dispose();
    }

    public static void maskAadharNumber1(BufferedImage aadharImage, String aadharNumber) {
        // Check Aadhar Number
        if (aadharNumber != null && aadharNumber.matches("\\d{12}")) {
            // Mask the first 8 digits of Aadhar Number
            String maskedAadharNumber = "********" + aadharNumber.substring(8);
            System.out.println("Masked Aadhar Number: " + maskedAadharNumber);

            // Image Processing
            try {
                // Example: Convert the Aadhar image to grayscale
                BufferedImage processedImage = new BufferedImage(aadharImage.getWidth(), aadharImage.getHeight(),
                        BufferedImage.TYPE_INT_RGB);
                processedImage.getGraphics().drawImage(aadharImage, 0, 0, null, null);
                System.out.println("");


                Graphics2D graphics = aadharImage.createGraphics();
                graphics.drawString("XXXXXXXX" + aadharNumber.substring(8), x, y);
                // Save the processed image to a file 
                File outputImageFile = new File("processed_aadhar_image.jpg");
                ImageIO.write(processedImage, "jpg", outputImageFile);

                System.out.println("Aadhar Image Processed and Saved Successfully.");
            } catch (IOException e) {
                System.out.println("Error processing Aadhar Image: " + e.getMessage());
            }
        } else {
            System.out.println("Invalid Aadhar Number");
        }
    }
}
