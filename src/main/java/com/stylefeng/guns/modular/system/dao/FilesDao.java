package com.stylefeng.guns.modular.system.dao;

import com.stylefeng.guns.common.persistence.model.FileUploadSpy;

/**
 * 文件上传Dao
 *
 * @author fengshuonan
 * @Date 2018-06-28 18:56:19
 */
public interface FilesDao {

    int uploadFiles(FileUploadSpy fileUploadSpy);


}
