package com.example.academickg.utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

public class CreateImageCode {
    private int height = 40; // 设置高度40
    private int width = 160; // 设置宽度160
    private int code_count = 5; // 验证码长度
    private int line_count = 20; // 干扰线数量
    private String code = null; // 验证码
    private BufferedImage bufferedImage;
    Random random = new Random();
    // 无参构造
    public CreateImageCode(){createImage();}
    // 有参构造
    public CreateImageCode(int width, int height, int code_count){
        this.height = height;
        this.width = width;
        this.code_count = code_count;
        createImage();
    }
    public CreateImageCode(int width, int height, int code_count, int line_count){
        this.height = height;
        this.width = width;
        this.code_count = code_count;
        this.line_count = line_count;
        createImage();
    }
    public void createImage(){
        // 字体设置
        int fontWidth = width / code_count;  // 字体宽度
        int fontHeight = height - 5; // 字体长度
        int codeY = height - 8;

        // 生成BufferedImage对象
        bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        // 图像操作
        Graphics graphics = bufferedImage.getGraphics();
        // 设置背景色
        graphics.setColor(getRandColor(200,250));
        // 填充矩形
        graphics.fillRect(0,0,width, height);
        // 设置字体
        Font font = new Font("Fixedsys", Font.BOLD, fontHeight);
        graphics.setFont(font);

        // 设置干扰线
        for (int i = 0; i < line_count; i++) {
            int xs = random.nextInt(width);
            int ys = random.nextInt(height);
            int xe = xs + random.nextInt(width);
            int ye = ys + random.nextInt(height);
            graphics.setColor(getRandColor(1,255));
            graphics.drawLine(xs, ys, xe, ye);
        }
        // 噪点
        float yawpRate = 0.01f;
        int area = (int) (yawpRate * width * height);
        for (int i = 0; i < area; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            bufferedImage.setRGB(x, y, random.nextInt(255));
        }

        // 得到随机字符
        String str1 = randomStr(code_count);
        this.code = str1;
        for (int i = 0; i < code_count; i++) {
            String strRand = str1.substring(i, i+1);
            graphics.setColor(getRandColor(1,255));
            graphics.drawString(strRand, i * fontWidth + 3, codeY);
        }
    }

    private Color getRandColor(int fc, int bc){
        if (fc < 0 || fc > 255) fc=255;
        if (bc < 0 || bc > 255) bc=255;
        int r = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
         return new Color(r, b, g);
    }

    private String randomStr(int num){
        String str1 = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" +
                "abcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder str2 = new StringBuilder();
        int len = str1.length() - 1;
        double r;
        // Math.random()生成随机0-1的浮点数
        for (int i = 0; i < num; i++) {
            r = (Math.random()) * len;
            str2.append(str1.charAt((int) r));
        }
        return str2.toString();
    }
    public void write(OutputStream sos) throws IOException {
        ImageIO.write(bufferedImage, "png", sos);
        sos.close();
    }
    public String getCode() {return code.toLowerCase();}
}
