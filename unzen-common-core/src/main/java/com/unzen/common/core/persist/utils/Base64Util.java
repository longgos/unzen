package com.unzen.common.core.persist.utils;

import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.apache.commons.codec.binary.Base64;

import com.unzen.base.context.UnzenConsts;
import com.unzen.base.lang.Consts;

public class Base64Util {

	/**
	 * 图片转字符串
	 * 
	 * @return
	 */
	public String getImageBinary(String imagePath, String imageType) {
		File f = new File(imagePath);
		BufferedImage bi;
		try {
			bi = ImageIO.read(f);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(bi, imageType, baos);
			byte[] bytes = baos.toByteArray();

			return Base64.encodeBase64String(bytes).trim();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	// base64字符串转化成图片
	public static boolean generateImage(String imgStr) {
		try {
			if (imgStr == null) {
				return false;
			}
			byte[] b = Base64.decodeBase64(imgStr);
			String avatorName =Consts.AVATARPATH+UUID.randomUUID()+Consts.IMGTYPE;
			ByteArrayInputStream stream = new ByteArrayInputStream(b);
			RenderedImage bl = ImageIO.read(stream);
			File w2 = new File(avatorName);
			if (!w2.exists()) {
				w2.createNewFile();
				System.out.println("no exist=====");
			}
			// 不管输出什么格式图片，此处不需改动
			return ImageIO.write(bl, Consts.IMGTYPE, w2);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	 //base64字符串转化成图片  
    public static String GenerateImage(String imgStr)  
    {   //对字节数组字符串进行Base64解码并生成图片  
        if (imgStr == null) //图像数据为空  
            return null;  
//        BASE64Decoder decoder = new BASE64Decoder();  
        try   
        {  
            //Base64解码  
        	byte[] b = Base64.decodeBase64(imgStr);
            for(int i=0;i<b.length;++i)  
            {  
                if(b[i]<0)  
                {//调整异常数据  
                    b[i]+=256;  
                }  
            }  
            //生成jpeg图片  
            String name = UUID.randomUUID().toString();
            String imgFilePath = Consts.AVATARPATH+name+Consts.IMGTYPE;
            File f = new File(Consts.AVATARPATH);
			//如果文件夹不存在
			if(!f.exists()){
				f.mkdirs();
			}
            OutputStream out = new FileOutputStream(imgFilePath);      
            out.write(b);  
            out.flush();  
            out.close();  
            return name+Consts.IMGTYPE;  
        }   
        catch (Exception e)   
        {  
            return null;  
        }  
    }  
}
