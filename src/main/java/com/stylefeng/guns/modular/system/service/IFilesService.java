package com.stylefeng.guns.modular.system.service;

import com.stylefeng.guns.common.persistence.model.FileUploadSpy;
import com.stylefeng.guns.core.util.FilesUtil;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 文件上传Service
 *
 * @author fengshuonan
 * @Date 2018-06-28 18:56:19
 */
public interface IFilesService {

    /**
     * 上传文件,并保存信息至数据库
     * @param request
     * @param file
     * @return string
     */
    String uploadFiles(HttpServletRequest request,MultipartFile[] file);

    /**
     * 根据Id查找上传文件记录
     * @param id
     * @return
     */
    FileUploadSpy findOrbyid(String id);

    /**
     * 获取所有的上传信息
     * @return
     */
    List<FileUploadSpy> getAllfiles();

}
