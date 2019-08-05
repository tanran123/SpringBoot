package com.ginko.driver.api.image;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Base64;

public class ImageBuffer {
    public final static Logger LOGGER = LoggerFactory.getLogger(ImageBuffer.class);

    public static BufferedImage Image;

    /**
     * 单例获取图片
     *
     * @return
     */
    public static synchronized  BufferedImage getFile() {
        if (Image == null) {
            File file = new File("D:/test2.jpg");
            try {
                Image = ImageIO.read(file);
            } catch (IOException e) {
                LOGGER.warn("读取图片失败");
            }
        }
        return Image;
    }

    /**
     * @param x x坐标
     * @param y y坐标
     * @param r rgba颜色
     * @param g
     * @param b
     * @param a
     */
    public static void updatePxColor(int x, int y, int r, int g, int b, int a) {
        Color color = new Color(r, g, b, a);
        ImageBuffer.getFile().setRGB(x, y, color.getRGB());
    }

    public static void main(String[] args) {
        ImageBuffer.updatePxColor(800, 600, 255, 255, 255, 255);
        Base64.Encoder encoder = Base64.getEncoder();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();// 字节流
        try {
            ImageIO.write(ImageBuffer.getFile(), "png", baos);// 写出到字节流
            ImageIO.write(ImageBuffer.getFile(), "png", new File("D:/test3.jpg"));
        } catch (IOException e) {
            LOGGER.warn("读取图片失败");
        }
        byte[] bytes = baos.toByteArray();
        // 编码成base64
        String jpg_base64 = encoder.encodeToString(bytes);
        System.out.println("data:image/jpg;base64,"+jpg_base64);


    }
}
