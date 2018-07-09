package com.stylefeng.guns.modular.system.service.impl;

import com.stylefeng.guns.common.persistence.model.FileUploadSpy;
import com.stylefeng.guns.core.util.FilesUtil;
import com.stylefeng.guns.core.util.Result;
import com.stylefeng.guns.modular.system.dao.FilesDao;
import com.stylefeng.guns.modular.system.dao.LeaveDao;
import com.stylefeng.guns.modular.system.service.IFilesService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

/**
 * 文件上传Dao
 *
 * @author fengshuonan
 * @Date 2018-06-28 18:56:19
 */
@Service
public class FilesServiceImpl implements IFilesService {

    @Resource
    LeaveDao leaveDao;

    @Override
    @Transactional
    public String uploadFiles(HttpServletRequest request, MultipartFile[] file) {
        try {
            FilesUtil filesUtil = new FilesUtil();
            List<FileUploadSpy> list = filesUtil.uploadFile(request,file);
            leaveDao.uploadFiles(list);
            if (list!=null){
                return Result.toClient(true,"上传成功");
            }
            return Result.toClient(false,"上传失败");
        } catch (IOException e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    @Override
    public FileUploadSpy findOrbyid(String id) {
        return leaveDao.findOrbyid(id);
    }

    @Override
    public List<FileUploadSpy> getAllfiles() {
        return leaveDao.getAllfiles();
    }
}
