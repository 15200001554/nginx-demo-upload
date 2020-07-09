package com.example.springbootdemo.util;


import com.emc.ecs.nfsclient.nfs.NfsCreateMode;
import com.emc.ecs.nfsclient.nfs.NfsSetAttributes;
import com.emc.ecs.nfsclient.nfs.io.Nfs3File;
import com.emc.ecs.nfsclient.nfs.io.NfsFileInputStream;
import com.emc.ecs.nfsclient.nfs.io.NfsFileOutputStream;
import com.emc.ecs.nfsclient.nfs.nfs3.Nfs3;
import com.emc.ecs.nfsclient.rpc.CredentialUnix;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @版本 V1.0
 * @描述 NFS工具类
 */
public class NfsUtil {
   /* private static final String NFS_IP = "192.168.0.XXX";
    private static final String NFS_DIR = "/home/wzh/nfs";*/

    private static final String NFS_IP = "123.57.41.174";
    private static final String NFS_DIR = "/opt/share";//远程文件目录位置

    /**
     * 上传文件到NFS服务器
     *
     * @param path     NFS 存储的相对路径
     * @param fileName 文件名称包括文件后缀
     * @param content  文件二进制内容
     * @return
     */
    public static boolean upload(String path, String fileName, byte[] content) {
        NfsFileOutputStream outputStream = null;

        NfsSetAttributes nfsSetAttr = new NfsSetAttributes();
        nfsSetAttr.setMode((long) (0x00100 + 0x00080 + 0x00040 + 0x00020 + 0x00010 + 0x00008 + 0x00004 + 0x00002));

        try {
            Nfs3 nfs3 = new Nfs3(NFS_IP, NFS_DIR, new CredentialUnix(-2, -2, null), 3);
            String paths[] = path.substring(1).split("/");//去掉第一个/之后进行分割处理

            StringBuilder p = new StringBuilder();

            //首先判断目录是否存在，如果不存在则进行创建目录
            for (String s : paths) {
                p.append("/").append(s);
                Nfs3File filePath = new Nfs3File(nfs3, p.toString());
                if (!filePath.exists()) {
                    filePath.mkdir(nfsSetAttr);
                }
            }


            //创建文件
            Nfs3File desFile = new Nfs3File(nfs3, path + "/" + fileName);
            desFile.create(NfsCreateMode.GUARDED, nfsSetAttr, null);

            outputStream = new NfsFileOutputStream(desFile);
            outputStream.write(content);

            return true;
        } catch (IOException ex) {
            Logger.getLogger(NfsUtil.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (null != outputStream) {
                try {
                    outputStream.close();
                } catch (IOException ex) {
                    Logger.getLogger(NfsUtil.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        return false;
    }

    /**
     * 文件下载
     *
     * @param filePath NFS上面的文件路径信息
     * @return
     */
    public static byte[] download(String filePath) {
        ByteArrayOutputStream bos = null;

        NfsFileInputStream inputStream = null;
        BufferedInputStream bis = null;

        try {
            Nfs3 nfs3 = new Nfs3(NFS_IP, NFS_DIR, new CredentialUnix(-2, -2, null), 3);
            Nfs3File file = new Nfs3File(nfs3, filePath);

            inputStream = new NfsFileInputStream(file);

            bis = new BufferedInputStream(inputStream);
            bos = new ByteArrayOutputStream();

            int date = -1;
            while ((date = bis.read()) != -1) {
                bos.write(date);
            }

            return bos.toByteArray();
        } catch (IOException ex) {
            Logger.getLogger(NfsUtil.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (null != bos) {
                try {
                    bos.close();
                } catch (IOException ex) {
                    Logger.getLogger(NfsUtil.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            if (null != bis) {
                try {
                    bis.close();
                } catch (IOException ex) {
                    Logger.getLogger(NfsUtil.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            if (null != inputStream) {
                try {
                    inputStream.close();
                } catch (IOException ex) {
                    Logger.getLogger(NfsUtil.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        return null;
    }
}