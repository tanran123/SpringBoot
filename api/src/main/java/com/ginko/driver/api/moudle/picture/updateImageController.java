package com.ginko.driver.api.moudle.picture;

import com.ginko.driver.api.image.ImageBuffer;
import com.ginko.driver.common.entity.MsgConfig;
import com.ginko.driver.framework.dao.MongoPxDaoImp;
import com.ginko.driver.framework.entity.PxEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

@RestController
@RequestMapping("/image")
public class updateImageController {
    public final static Logger LOGGER = LoggerFactory.getLogger(updateImageController.class);
    @Autowired
    private MongoPxDaoImp mongoPxDaoImp;


    /**
     * 修改像素点
     * @param x 坐标
     * @param y  坐标
     * @param r  RGBA
     * @param g
     * @param b
     * @param a
     * @return
     */
    @RequestMapping(value = "/updateImage", method = RequestMethod.POST)
    public MsgConfig updateImage(@RequestBody  PxEntity pxEntity) {

        ImageBuffer.updatePxColor(pxEntity.getX(), pxEntity.getY(), pxEntity.getR(), pxEntity.getG(), pxEntity.getB(), pxEntity.getA());
        return new MsgConfig("200", "修改成功", null);
    }

    /**
     * 获取内存中的图片
     * @return
     */
    @RequestMapping(value = "/getImage", method = RequestMethod.POST)
    public MsgConfig getImage(){
        Base64.Encoder encoder = Base64.getEncoder();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();// 字节流
        try {
            ImageIO.write(ImageBuffer.getFile(), "gif", baos);// 写出到字节流
        } catch (IOException e) {
            return new MsgConfig("500","读取图片失败",null);
        }
        byte[] bytes = baos.toByteArray();
        // 编码成base64
        String jpg_base64 = encoder.encodeToString(bytes);
        return new MsgConfig("200",null,"data:image/gif;base64," + jpg_base64);
    }

}
