package com.example.springbootdemo.util;

import com.emc.ecs.nfsclient.nfs.io.Nfs3File;
import com.emc.ecs.nfsclient.nfs.io.NfsFileInputStream;
import com.emc.ecs.nfsclient.nfs.io.NfsFileOutputStream;
import com.emc.ecs.nfsclient.nfs.nfs3.Nfs3;
import com.emc.ecs.nfsclient.rpc.CredentialUnix;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

public class NfsTransferFile {

    private static final String NFS_IP = "106.52.49.73";
    private static final String NFS_DIR = "/opt/share";//远程文件目录位置
  /*  private static final String NFS_IP = "192.168.98.53";
    private static final String NFS_DIR = "/opt/share";//远程文件目录位置*/

    public static void main(String[] args) {
//        uploadFileToNfs(new MultipartFile() {
//            @Override
//            public String getName() {
//                return null;
//            }
//
//            @Override
//            public String getOriginalFilename() {
//                return null;
//            }
//
//            @Override
//            public String getContentType() {
//                return null;
//            }
//
//            @Override
//            public boolean isEmpty() {
//                return false;
//            }
//
//            @Override
//            public long getSize() {
//                return 0;
//            }
//
//            @Override
//            public byte[] getBytes() throws IOException {
//                return new byte[0];
//            }
//
//            @Override
//            public InputStream getInputStream() throws IOException {
//                return null;
//            }
//
//            @Override
//            public void transferTo(File file) throws IOException, IllegalStateException {
//
//            }
//        });
        //downLoadFileFromNfs();
    }

    //上传本地文件到Nfs服务器指定目录
    public static String uploadFileToNfs(MultipartFile file) {
//        String localDir = "D:\\images\\1.jpeg";
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
          /*  //创建一个本地文件对象
            File localFile = new File(localDir);*/
            //获取本地文件的文件名，此名字用于在远程的Nfs服务器上指定目录创建同名文件
            String localFileName = file.getName();
            CredentialUnix credentialUnix = new CredentialUnix(-2, -2, null);
            /**
             * 服务器
             * 挂载目录
             * 凭证
             * 最大检索数
             */
            Nfs3 nfs3 = new Nfs3(NFS_IP, NFS_DIR, credentialUnix, 3);
            //创建远程服务器上Nfs文件对象
            Nfs3File NfsFile = new Nfs3File(nfs3, "/" + localFileName);
            //打开一个文件输入流
            inputStream = new BufferedInputStream(new FileInputStream((File) file));
            //打开一个远程Nfs文件输出流，将文件复制到的目的地
            outputStream = new BufferedOutputStream(new NfsFileOutputStream(NfsFile));

            //缓冲内存
            byte[] buffer = new byte[1024];
            while ((inputStream.read(buffer)) != -1) {
                outputStream.write(buffer);
            }
            System.out.println("文件上传完成！");
            return "文件上传完成！";
        } catch (Exception ex) {
            ex.printStackTrace();
           return  "文件上传完成！失败";
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    //从Nfs服务器上下载指定的文件到本地目录
    public static void downLoadFileFromNfs() {
        String NfsFileDir = "/look.txt";
        String localDir = "F:\\look\\";
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            Nfs3 nfs3 = new Nfs3(NFS_IP, NFS_DIR, new CredentialUnix(0, 0, null), 3);
            //创建远程服务器上Nfs文件对象
            Nfs3File nfsFile = new Nfs3File(nfs3, NfsFileDir);
            String localFileName = localDir + nfsFile.getName();
            //创建一个本地文件对象
            File localFile = new File(localFileName);
            //打开一个文件输入流
            inputStream = new BufferedInputStream(new NfsFileInputStream(nfsFile));
            //打开一个远程Nfs文件输出流，将文件复制到的目的地
            outputStream = new BufferedOutputStream(new FileOutputStream(localFile));

            //缓冲内存
            byte[] buffer = new byte[1024];

            while (inputStream.read(buffer) != -1) {
                outputStream.write(buffer);
            }
            System.out.println("文件下载完成！");
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
