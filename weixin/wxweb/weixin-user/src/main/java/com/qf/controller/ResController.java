package com.qf.controller;

import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.qf.entity.User;
import com.qf.entity.base.ResultEntity;
import com.qf.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @auther JC
 * @date 2020/4/15
 */
@RestController
@RequestMapping(value = "/res")
public class ResController {

    // tacker
    // storage
    @Autowired
    private FastFileStorageClient ffsc;

    @Value("${fastpath}")
    private String fdfsPath;

    @Autowired
    private IUserService userService;

    /**
     *
     * @param userId 用户id
     * @param file
     * @return
     */
    @RequestMapping(value = "/uploadFile")
    public ResultEntity uploadFile(Integer userId, MultipartFile file){
        System.out.println(file);
        String fileName = file.getOriginalFilename();

        // 获取文件的后缀
        String fielExName = fileName.substring(fileName.lastIndexOf(".")+1);
        try {

            // 上传头像到FastDFS
            StorePath storePath = ffsc.uploadImageAndCrtThumbImage(file.getInputStream(), file.getSize(), fielExName, null);
            String fullPath = storePath.getFullPath();

            // 获取大图和小图的路径
            String maxHead = fdfsPath+fullPath;
            String minHead = fdfsPath+fullPath.replaceAll("."+fielExName,"_100x100."+fielExName);

            // 修改用户表中的头像
            if(userId != null){
                User user = userService.selectById(userId);
                user.setMaxHead(maxHead);
                user.setMinHead(minHead);
                userService.update(user);
            }
            Map<String,String> map = new HashMap<>();
            map.put("maxHead",maxHead);
            map.put("minHead",minHead);
            return ResultEntity.success(map);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
