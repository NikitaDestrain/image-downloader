package com.image.downloader.shared.pojo;

import lombok.Data;

import java.io.Serializable;

@Data
public class ImageMetadata implements Serializable {
    private String fileName;
    private String fileExtension;
    private int width;
    private int height;
    private long fileSize;

    @Override
    public String toString() {
        return "ImageMetadata:\n" +
                "\t\t\tfileName = '" + fileName + '\'' + "\n" +
                "\t\t\tfileExtension = '" + fileExtension + '\'' + "\n" +
                "\t\t\twidth = " + width + "\n" +
                "\t\t\theight = " + height + "\n" +
                "\t\t\tfileSize = " + fileSize + " bytes";
    }
}
