package com.mmall.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @Discription
 * @Author:liqiwen
 * @Date:2020/4/27 20:48
 */
public interface IFileService {

    String upload(MultipartFile file, String path);
}
