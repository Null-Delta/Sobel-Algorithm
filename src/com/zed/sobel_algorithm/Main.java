package com.zed.sobel_algorithm;
import org.w3c.dom.css.RGBColor;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageOutputStream;
import java.awt.*;
import java.applet.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        System.out.println("his application was created to study the operator Sobel\nfor information on commands write \"/help\"");
        String command = "";
        while (true) {
            Scanner s = new Scanner(System.in);
            command = s.nextLine();

            if(command.equals("/make image")) {
                File f = new File("images");
                File fls[] = f.listFiles();
                System.out.println("----------Select Image----------");
                for (int i = 0; i < fls.length; i++) {
                    System.out.println(i + " " + fls[i].getName());
                }
                System.out.println("-1 Exit");
                System.out.println("--------------------------------");
                int ans;
                ans = Integer.parseInt(s.nextLine());
                while(ans < -1 || ans > fls.length){
                    System.out.println("please select correct num");
                    ans = Integer.parseInt(s.nextLine());
                }
                if(ans != -1){
                    try {
                        BufferedImage img = ImageIO.read(fls[ans]);
                        ImageIO.write(Sobel(img),"png",new File("new images/" + fls[ans].getName()));
                        System.out.println("Done");
                    } catch (IOException e) {
                        System.out.println("Image not found");
                        e.printStackTrace();
                    }
                }

            } else if(command.equals("/help")) {
                System.out.println("Commands list:");
                System.out.println("/make image : select image for refactoring");
            } else
                System.out.println("Commend not found");
            }
        }

        //try {
//            File f = new File("test.jpg");
//            BufferedImage img = ImageIO.read(f);
//
//            File f2 = new File("test5.png");
//
//            ImageIO.write(Sobel(img),"png",f2);
//            System.out.println(f2.getAbsolutePath());
//            System.out.println("Done");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    //}

    public static BufferedImage Sobel(BufferedImage img){
        int[][] gy = new int[][] {
                {-1,-2,-1},
                {0,0,0},
                {1,2,1},
        };
        int[][] gx = new int[][] {
                {-1,0,1},
                {-2,0,2},
                {-1,0,1},
        };

        BufferedImage newimg = new BufferedImage(img.getWidth(),img.getHeight(),BufferedImage.TYPE_INT_ARGB);

        for(int x = 1; x < img.getWidth() - 1; x++)
            for(int y = 1; y < img.getHeight() - 1; y++){
                int fx = 0,fy = 0;
                int r = 0,g = 0,b = 0;

                for(int i = 0; i < 3; i++)
                    for(int j = 0; j < 3; j++) {
                        int c = img.getRGB(x + i - 1, y + j - 1);
                        r = (c >> 16) & 0xFF;
                        g = (c >> 8) & 0xFF;
                        b = (c >> 0) & 0xFF;

                        c = (r + g + b) / 3;

                        fx += gx[i][j] * c;
                        fy += gy[i][j] * c;
                    }

                float w = 0.35f;
                if(Math.sqrt(Math.pow(fx,2) + Math.pow(fy,2)) * w <= 255) {
                    newimg.setRGB(x, y, new Color((int)(Math.sqrt(Math.pow(fx,2) + Math.pow(fy,2))* w) ,(int)(Math.sqrt(Math.pow(fx,2) + Math.pow(fy,2)) * w),(int)(Math.sqrt(Math.pow(fx,2) + Math.pow(fy,2)) * w)).getRGB());
                }

            }
        return newimg;
    }
}
