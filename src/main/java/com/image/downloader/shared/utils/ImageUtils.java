package com.image.downloader.shared.utils;

import com.image.downloader.shared.pojo.ImageMetadata;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageUtils {
    private static volatile ImageUtils instance;

    private ImageUtils() {
    }

    public static ImageUtils getInstance() {
        ImageUtils localInstance = instance;
        if (localInstance == null) {
            synchronized (ImageUtils.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new ImageUtils();
                }
            }
        }
        return localInstance;
    }

    public File readImageFile(String imageName, String imageExtension, String imagePath) {
        String fullPath = imagePath + "/" + imageName + "." + imageExtension;
        System.out.println("[INFO]: Reading " + fullPath + " completed");
        return new File(fullPath);
    }

    public BufferedImage readImage(File imageFile) {
        try {
            BufferedImage image = ImageIO.read(imageFile);
            return image;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void writeImage(BufferedImage image, String imageName, String imageExtension, String imagePath) {
        try {
            String fullPath = imagePath + "/" + imageName + "." + imageExtension;
            File imageFile = new File(fullPath);
            ImageIO.write(image, imageExtension, imageFile);
            System.out.println("[INFO]: Writing " + fullPath + " completed");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ImageMetadata getImageMetadata(BufferedImage image, String imageName, String imageExtension, long fileSize) {
        ImageMetadata imageMetadata = new ImageMetadata();
        imageMetadata.setFileName(imageName);
        imageMetadata.setFileExtension(imageExtension);
        imageMetadata.setHeight(image.getHeight());
        imageMetadata.setWidth(image.getWidth());
        imageMetadata.setFileSize(fileSize);
        return imageMetadata;
    }
}
