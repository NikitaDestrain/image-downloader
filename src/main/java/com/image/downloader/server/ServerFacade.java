package com.image.downloader.server;

import com.image.downloader.shared.other.Constants;
import com.image.downloader.shared.pojo.ImageMetadata;
import com.image.downloader.shared.utils.ImageUtils;

import java.awt.image.BufferedImage;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerFacade {
    public static void main(String[] args) throws IOException {
        System.out.println("======================SERVER========================");
        System.out.println("[INFO]: Server port - " + Constants.DEFAULT_SERVER_PORT);
        ImageUtils imageUtils = ImageUtils.getInstance();

        File imageFile = imageUtils.readImageFile(Constants.DOWNLOAD_IMAGE_NAME,
                Constants.DOWNLOAD_IMAGE_EXTENSION,
                Constants.DOWNLOAD_IMAGE_SERVER_PATH);
        // process image info
        BufferedImage storedImage = imageUtils.readImage(imageFile);
        ImageMetadata imageMetadata = imageUtils.getImageMetadata(storedImage,
                Constants.DOWNLOAD_IMAGE_NAME,
                Constants.DOWNLOAD_IMAGE_EXTENSION,
                imageFile.length());
        System.out.println("[INFO]: " + imageMetadata);

        ServerSocket serverSocket = new ServerSocket(Constants.DEFAULT_SERVER_PORT);
        // we will work a whole day :)
        while (true) {
            try {
                System.out.println();
                Thread.sleep(500);
                System.out.println("[INFO]: Waiting for new client...");
                Socket clientSocket = serverSocket.accept();
                System.out.println("[INFO]: Client connected");
                OutputStream outputStream = clientSocket.getOutputStream();
                // create an object output stream from the output stream so we can send an object through it
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);

                objectOutputStream.writeObject(imageMetadata);
                objectOutputStream.writeObject(imageFile);
                objectOutputStream.flush();
                System.out.println("[INFO]: Image with metadata has been sent");
                objectOutputStream.close();
                System.out.println("[INFO]: Channel closed");
                clientSocket.close();
                System.out.println("[INFO]: Client disconnected");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
