package com.tt.tutorials.image.thumbnailator;

import net.coobird.thumbnailator.filters.Caption;
import net.coobird.thumbnailator.geometry.Position;
import net.coobird.thumbnailator.geometry.Positions;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.imageio.ImageIO;

/**
 * 图片处理工具类
 *
 * @author Thomson Tang
 */
public class ImageUtils {

    public static void addCaption(String originalImagePath, String captionText) {
        try {
            BufferedImage originalImage = ImageIO.read(new File(originalImagePath));

            Font font = new Font("Monaco", Font.PLAIN, 14);
            Color color = Color.RED;
            Position position = Positions.BOTTOM_RIGHT;
            Caption caption = new Caption(captionText, font, color, position, 4);
            BufferedImage targetImage = caption.apply(originalImage);
            ImageIO.write(targetImage, "png", new File("/Users/thomsontang/temp/caption2.png"));
            System.out.println("done");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formatDateTime = now.format(formatter);
        ImageUtils.addCaption("/Users/thomsontang/Pictures/annotate/kaoqing.png", formatDateTime);
    }
}
