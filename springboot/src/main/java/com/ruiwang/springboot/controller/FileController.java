package com.ruiwang.springboot.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;

import com.ruiwang.springboot.common.Result;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;

/**
 * @BelongsProject: springboot-vue-demo
 * @BelongsPackage: com.ruiwang.springboot.controller
 * @Author: ruiwang
 * @CreateTime: 2023-04-19 22:34
 * @Description:
 * @Version:1.0
 */
@RestController
@RequestMapping(value = "/files")
public class FileController {
    @Value("${server.port}")
    private String port;
    private static final String ip = "http://localhost";


    /**
     * 上传接口
     *
     * @param file
     * @return
     * @throws IOException
     */
    @PostMapping("/upload")
    public Result<?> upload(@RequestPart("file") MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();       // 获取文件名称
        // 定义文件的唯一表示
        String flag = IdUtil.fastSimpleUUID();
        String rootFilePath = System.getProperty("user.dir") + "/springboot/src/main/resources/files/" + flag + "_" + originalFilename;
        FileUtil.writeBytes(file.getBytes(), rootFilePath);          // 把文件写入到上传的路径

        System.out.println(Result.success(ip + ":" + port + "/files/" + flag));
        return Result.success(ip + ":" + port + "/files/" + flag);       // 返回结果url

    }

    /**
     * 富文本文件上传接口
     *
     * @param file
     * @return
     * @throws IOException
     */
    @PostMapping("/editor/upload")
    public JSON editorupload(@RequestPart("file") MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();       // 获取文件名称
        // 定义文件的唯一表示
        String flag = IdUtil.fastSimpleUUID();
        String rootFilePath = System.getProperty("user.dir") + "/springboot/src/main/resources/files/" + flag + "_" + originalFilename;
        FileUtil.writeBytes(file.getBytes(), rootFilePath);          // 把文件写入到上传的路径

        String url = ip + ":" + port + "/files/" + flag;
        JSONObject json = new JSONObject();
        json.set("errno", 0);
        JSONArray arr = new JSONArray();
        JSONObject data = new JSONObject();
        arr.add(data);
        data.set("url", url);
        json.set("data", arr);


        return json;       // 返回结果url

    }


    /**
     * 下载接口
     *
     * @param response
     */
    @GetMapping("/{flag}")
    public void getFiles(@PathVariable String flag, HttpServletResponse response) {
        OutputStream os; //新建一个输出流
        String basePath = System.getProperty("user.dir") + "/springboot/src/main/resources/files/"; //定位文件上传的根路径
        List<String> fileNames = FileUtil.listFileNames(basePath);  //获取所有文件名称
        String fileName = fileNames.stream().filter(name -> name.contains(flag)).findAny().orElse("");       //找到参数一直的文件
        System.out.println(fileName);
        try {
            if (StrUtil.isNotEmpty(fileName)) {
                response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
                response.setContentType("application/octet-stream");
                byte[] bytes = FileUtil.readBytes(basePath + fileName); //通过文件路径读取文件字节流
                os = response.getOutputStream();        // 通过输出流返回文件
                os.write(bytes);
                os.flush();
                os.close();
            }
        } catch (IOException e) {
            System.out.println("文件下载失败");
        }
    }

}
