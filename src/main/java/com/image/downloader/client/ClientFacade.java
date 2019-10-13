package com.image.downloader.client;

import com.image.downloader.shared.other.Constants;
import com.image.downloader.shared.pojo.ImageMetadata;
import com.image.downloader.shared.utils.ImageUtils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;

public class ClientFacade {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        System.out.println("======================CLIENT========================");
        ImageUtils imageUtils = ImageUtils.getInstance();

        System.out.println("[INFO]: Trying to connect to server with port " + Constants.DEFAULT_SERVER_PORT);
        Socket serverSocket = new Socket("localhost", Constants.DEFAULT_SERVER_PORT);
        InputStream inputStream = serverSocket.getInputStream();
        ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
        System.out.println("[INFO]: Connected");

        ImageMetadata imageMetadata = (ImageMetadata) objectInputStream.readObject();
        System.out.println("[INFO]: Image metadata received");
        File receivedImageFile = (File) objectInputStream.readObject();
        System.out.println("[INFO]: Image received");
        serverSocket.close();
        System.out.println("[INFO]: Disconnected");
        System.out.println("[INFO]: " + imageMetadata);

        imageUtils.writeImage(imageUtils.readImage(receivedImageFile),
                imageMetadata.getFileName(),
                imageMetadata.getFileExtension(),
                Constants.DOWNLOAD_IMAGE_CLIENT_PATH
        );

        System.out.println("[INFO]: Bye-Bye");
    }
}
