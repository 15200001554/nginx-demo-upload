package com.example.springbootdemo.controller;

import com.example.springbootdemo.util.NfsTransferFile;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * 文件上传
 */
@RestController
@RequestMapping("/user")
public class FileController {

    @PostMapping(value = "/fileUpload")
    public String fileUpload(@RequestParam(value = "file") MultipartFile file) {
        if (file.isEmpty()) {
            System.out.println("请选择图片");
        }
        String fileName = file.getOriginalFilename();  // 文件名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));  // 后缀名
        String filePath = "D:/images/"; // 上传后的路径
        fileName = UUID.randomUUID() + suffixName; // 新文件名
        File dest = new File(filePath + fileName);
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        try {
            file.transferTo(dest);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //返回图片名称
        return fileName;
    }
    @PostMapping(value = "/fileUploadMy")
    public String  fileUploadMy(@RequestParam(value = "file") MultipartFile file) {
        if (file.isEmpty()) {
            System.out.println("请选择图片");
        }
        String s = NfsTransferFile.uploadFileToNfs(file);
       return s;
    }


}