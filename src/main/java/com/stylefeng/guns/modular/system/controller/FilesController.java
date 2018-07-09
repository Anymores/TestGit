package com.stylefeng.guns.modular.system.controller;

import com.stylefeng.guns.common.controller.BaseController;
import com.stylefeng.guns.common.persistence.model.FileUploadSpy;
import com.stylefeng.guns.core.util.FilesUtil;
import com.stylefeng.guns.core.util.Result;
import com.stylefeng.guns.modular.system.dao.LeaveDao;
import com.stylefeng.guns.modular.system.service.IFilesService;
import jcommon.Sys;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.jnlp.FileSaveService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 文件上传控制器
 *
 * @author fengshuonan
 * @Date 2018-06-28 18:56:19
 */
@Controller
@RequestMapping("/Files")
public class FilesController extends BaseController {

    @Resource
    LeaveDao leaveDao;

    @Resource
    IFilesService iFilesService;


    private String PREFIX = "/system/Files/";

    /**
     * 跳转到文件上传首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "Files.html";
    }

    @RequestMapping("/todownload")
    public  String toDownload(Model model){
        List<FileUploadSpy> list=iFilesService.getAllfiles();
        model.addAttribute("filelist",list);
        return PREFIX+"download.html";
    }

    /**
     * 文件上传
     */
    @RequestMapping(value = "/upload",method = RequestMethod.POST)
    @ResponseBody
    public String upload(HttpServletRequest request, @RequestParam("file")MultipartFile[] file) {
        return iFilesService.uploadFiles(request,file);
    }
    /**
     * 文件下载
     */
    @RequestMapping("/download")
    public void upload(HttpServletRequest req, HttpServletResponse response, FilesUtil filesUtil,String id) throws Exception {
        FileUploadSpy fileUploadSpy=iFilesService.findOrbyid(id);
         filesUtil.download(req,response,fileUploadSpy);
    }
}