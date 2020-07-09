package com.example;

import com.example.springbootdemo.util.NfsUtil;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @作者 y
 * @版本 V1.0
 * @描述
 */
public class FileTest {

    public static void main(String[] args) {
        String fileName = "1.jpeg";
        int hashcode = fileName.hashCode();
        int dir1 = hashcode & 0xf;  //0--15
        int dir2 = (hashcode & 0xf0) >> 4;  //0-15
        String path = "/" + dir1 + "/" + dir2;

        byte []file = fileToBytes("D:\\images\\1.jpeg");
        boolean flag = NfsUtil.upload(path, fileName, file);
        System.out.println("flag:"+flag);

        /*for(int i=0;i<3;i++){
            byte []buff = NfsUtil.download("/t01/t001/tt/ssptbin20180613.7z");
            bytesToFile(buff,"D:\\images\\1.jpeg"+i+".7z");
        }*/


    }

    public static void bytesToFile(byte[] buffer, final String filePath){

        File file = new File(filePath);

        OutputStream output = null;
        BufferedOutputStream bufferedOutput = null;

        try {
            output = new FileOutputStream(file);

            bufferedOutput = new BufferedOutputStream(output);

            bufferedOutput.write(buffer);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally{
            if(null!=bufferedOutput){
                try {
                    bufferedOutput.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if(null != output){
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static byte[] fileToBytes(String filePath) {
        byte[] buffer = null;
        File file = new File(filePath);

        FileInputStream fis = null;
        ByteArrayOutputStream bos = null;

        try {
            fis = new FileInputStream(file);
            bos = new ByteArrayOutputStream();

            byte[] b = new byte[1024];

            int n;

            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }

            buffer = bos.toByteArray();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FileTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FileTest.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (null != bos) {
                    bos.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(FileTest.class.getName()).log(Level.SEVERE, null, ex);
            } finally{
                try {
                    if(null!=fis){
                        fis.close();
                    }
                } catch (IOException ex) {
                    Logger.getLogger(FileTest.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        return buffer;
    }

}