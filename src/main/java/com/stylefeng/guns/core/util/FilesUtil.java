package com.stylefeng.guns.core.util;

import com.stylefeng.guns.common.persistence.model.FileUploadSpy;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class FilesUtil {
    /**
     * 多文件上传
     * @param request
     * @param file
     * @return
     * @throws IOException
     * @return FileUploadSpy
     */
    public List<FileUploadSpy> uploadFile(HttpServletRequest request, MultipartFile[] file) throws IOException {
        List<FileUploadSpy> list = new ArrayList();
            for (int i =0;i<file.length;i++){
                FileUploadSpy fileUploadSpy = new FileUploadSpy();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSS");
                String res = sdf.format(new Date());
                // uploads文件夹位置
                String rootPath = request.getSession().getServletContext().getRealPath("uploads/");
                // 原始名称
                String originalFileName = file[i].getOriginalFilename();
                // 新文件名
                String newFileName = "sliver" + res + originalFileName.substring(originalFileName.lastIndexOf("."));
                fileUploadSpy.setFileName(newFileName);
                // 创建年月文件夹
                Calendar date = Calendar.getInstance();
                File dateDirs = new File(date.get(Calendar.YEAR) + File.separator + (date.get(Calendar.MONTH)+1));
                // 新文件
                File newFile = new File(rootPath + File.separator + dateDirs + File.separator + newFileName);
                fileUploadSpy.setFileUrl(rootPath+"/"+dateDirs);
                fileUploadSpy.setUploadTime(new Date());
                list.add(fileUploadSpy);
                // 判断目标文件所在目录是否存在
                if( !newFile.getParentFile().exists()) {
                    // 如果目标文件所在的目录不存在，则创建父目录
                    newFile.getParentFile().mkdirs();
                }
                // 将内存中的数据写入磁盘
                try {
                    file[i].transferTo(newFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        return list;
    }

    /**
     * 文件下载
     * @param request
     * @param response
     * @param fileUploadSpy
     * @throws IOException
     */
    public void download(HttpServletRequest request, HttpServletResponse response,FileUploadSpy fileUploadSpy) throws IOException {
        File newFile = new File(fileUploadSpy.getFileUrl());
        String filename=fileUploadSpy.getFileName();
        File file=new File(newFile+File.separator+filename);
        String downloadFileName=new String(filename.getBytes("iso-8859-1"),"utf-8");
        response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(downloadFileName, "UTF-8"));
        FileInputStream in = new FileInputStream(file);
        OutputStream out = response.getOutputStream();
        byte buffer[] = new byte[1024];
        int len = 0;
        while ((len = in.read(buffer)) > 0) {
            out.write(buffer, 0, len);
        }
        in.close();
        out.close();
    }
}
