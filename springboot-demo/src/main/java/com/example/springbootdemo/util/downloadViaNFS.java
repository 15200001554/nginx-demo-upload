/*
package com.example.springbootdemo.util;

import java.io.File;
import java.io.IOException;

public class downloadViaNFS {
    public void downloadViaNFS(final String ip,final String user,final String password,final String dir)  {
//        log.debug("NFS download begin!");
        try {

            String url = "nfs://"+ip+"/"+dir;
            XFile xf = new XFile(url);
            if (xf.exists())
            {
                logger.debug("URL is OK!");
            }else
            {
                logger.debug("URL is bad!");
                return;
            }

            XFileExtensionAccessor nfsx = (XFileExtensionAccessor)xf.getExtensionAccessor();
            if(!nfsx.loginPCNFSD(ip, user, password))
            {
                logger.debug("login failed!");return;
            }

            String [] fileList = xf.list();
            XFile temp = null;
            long startTime = System.currentTimeMillis();
            int filesz = 0;
            for(String file:fileList)
            {
                temp = new XFile(url+"/"+file);
                XFileInputStream  in  = new XFileInputStream(temp)  ;
                XFileOutputStream out = new XFileOutputStream(tempDir+ File.separator+file);

                int c;
                byte[] buf = new byte[8196];



                while ((c = in.read(buf)) > 0) {
                    filesz += c;
                    out.write(buf, 0, c);
                }

                logger.debug(file +" is downloaded!");
                in.close();
                out.close();
                if (temp.canWrite())
                {
                    temp.delete();
                    logger.debug(file + " is deleted!");
                }else
                {
                    logger.debug(file + " can not be delted!");
                }
            }
            long endTime = System.currentTimeMillis();
            long timeDiff = endTime - startTime;
            int rate = (int) ((filesz /1000) / (timeDiff / 1000.0));
            logger.debug(filesz + " bytes copied @ " + rate + "Kb/sec");

        }catch (IOException e) {
            logger.debug(e);
        }

    }
}
*/
