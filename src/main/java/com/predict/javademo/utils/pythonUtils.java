package com.predict.javademo.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class pythonUtils {

    //python脚本文件夹
    final static String pythonUrl = "E:\\Pytorch2\\";

    public static String pytest(MultipartFile[] image,String model) {
       List<String> respond = new ArrayList<>();
        Process proc;

          try {
              for (MultipartFile images:image) {
                  String imgName = writeImage(images);
                  String[] cmdAttr = {"D:\\pyen\\python.exe", pythonUrl + model + ".py", imgName};

                  proc = Runtime.getRuntime().exec(cmdAttr);// 执行py文件
                  BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream(), "gb2312"));
                  List<String> line = new ArrayList<>();
                  String tmpLine;
                  while ((tmpLine = in.readLine()) != null) {
                      line.add(tmpLine);
                  }
                  String res = line.get(line.size() - 1);
                  System.out.println(res);
                  respond.add(res);
                  in.close();
                  proc.waitFor();
                  deleteImage(imgName);
              }
              return respond.toString();

          } catch (IOException e) {

              e.printStackTrace();
          } catch (InterruptedException e) {

              e.printStackTrace();
          }

        return "error";
    }

    private static String writeImage(MultipartFile originImg) {
        String originImgName = originImg.getOriginalFilename();
        String type = originImgName.substring(originImgName.lastIndexOf("."));
        String id = UUID.randomUUID().toString();

        File file = new File(pythonUrl + "images\\" + id + type);

        try {
            originImg.transferTo(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return id+type;

    }

    private static void deleteImage(String imgName){
        File file = new File(pythonUrl + "images\\" + imgName);
        file.delete();
    }
}

