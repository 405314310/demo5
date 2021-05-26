package com.lyz.demo5.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/file")
public class FileController {

    @Value("${filepath}")
    private String filePath;

    /**
     * 上传文件
     * @param multipartFile
     * @return
     */
    @PostMapping("/upload")
    public Object upload(@RequestParam("lyzfile")MultipartFile multipartFile){ // RequestParam里字符串和前端设置的name属性一样
        if(multipartFile.isEmpty()){
            return "未选择文件";
        }

        /**
         * 将当前时间加入文件名最后
         */

        String fileName = multipartFile.getOriginalFilename();
        if(fileName==null){
            return "未选择文件";
        }
        int docNum = fileName.lastIndexOf(".");
        String prefix = fileName.substring(0,docNum); //获取文件名 不含文件后缀名
        String suffix = fileName.substring(docNum,fileName.length()); //获取文件后缀名
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd-HH_mm_ss_SS");
        String dateStr = simpleDateFormat.format(date);
        String newFileName = prefix+"-"+dateStr+suffix; //将当前时间加入文件名最后

        int code = 2;

        File targetFile = new File(filePath);
        if (!targetFile.exists()) { //如果没有文件路径   创建路径文件夹
            targetFile.mkdirs();
        }
        if(code ==1 ){
            FileOutputStream out = null;
            try {

                out = new FileOutputStream(filePath+ newFileName);
                out.write(multipartFile.getBytes());

            } catch (IOException e) {
                e.printStackTrace();
                return e.getMessage();
            } finally {
                if(out!=null){
                    try {
                        out.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        if(code == 2){
            File newFile = new File(filePath+newFileName);
            try {
                multipartFile.transferTo(newFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "upload success";
    }

    @PostMapping("/upload2")
    public String fileUpload(@RequestParam("lyzfile")MultipartFile[] files ,@RequestParam("filename") String filesName){
        String filePath2 = "";
        System.out.println(files.length);
        File targetFile = new File(filePath+"/"+filesName);
        if (!targetFile.exists()) { //如果没有文件路径   创建路径文件夹
            targetFile.mkdirs();
        }
        // 多文件上传
        for (MultipartFile file : files){
            // 上传简单文件名
            String originalFilename = file.getOriginalFilename();
            System.out.println(originalFilename);
            // 存储路径
            filePath2 = new StringBuilder(filePath)
                  /*  .append(System.currentTimeMillis())*/
                    .append(filesName)
                    .append("/")
                    .append(originalFilename)
                    .toString();
            try {
                // 保存文件
                file.transferTo(new File(filePath2));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return filePath2;
    }

    @GetMapping("/download")
    public void download(HttpServletResponse res,@RequestParam("parmas") String parmas) throws FileNotFoundException, UnsupportedEncodingException {
        System.out.println(parmas);
        String fileName = "wifi热点设置.txt";
        System.out.println(fileName);
        res.setHeader("content-type", "application/octet-stream");
        res.setContentType("application/octet-stream");
        res.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("utf-8"),"iso-8859-1"));
        byte[] buff = new byte[1024];
        BufferedInputStream bis = null;
        OutputStream os = null;
        File f1= new File("E://file/"+fileName);

        System.out.println("f1name:"+f1.getName());
        try {
            os = res.getOutputStream();
            bis = new BufferedInputStream(new FileInputStream(new File("E://file/"
                    + fileName)));
            int i = bis.read(buff);
            while (i != -1) {
                os.write(buff, 0, buff.length);
                os.flush();
                i = bis.read(buff);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("success");
    }
}
