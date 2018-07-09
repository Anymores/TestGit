package com.stylefeng.guns.modular.system.controller;

import com.stylefeng.guns.common.constant.tips.Tip;
import com.stylefeng.guns.common.controller.BaseController;
import com.stylefeng.guns.common.exception.BizExceptionEnum;
import com.stylefeng.guns.common.exception.BussinessException;
import com.stylefeng.guns.common.persistence.dao.LeaveGhlMapper;
import com.stylefeng.guns.common.persistence.model.*;
import com.stylefeng.guns.core.shiro.ShiroUser;
import com.stylefeng.guns.core.util.CodeUtil;
import com.stylefeng.guns.core.util.GetMessage;
import com.stylefeng.guns.core.util.MapChcha;
import com.stylefeng.guns.modular.system.dao.LeaveDao;
import com.stylefeng.guns.modular.system.service.ILeaveService;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 考勤控制器
 *
 * @author 史鹏宇
 * @Date 2018-06-19 09:22:07
 */
@Controller
@RequestMapping("/leave")
public class LeaveController extends BaseController {

    private String PREFIX = "/system/leave/";

    @Resource
    LeaveDao leaveDao;

    @Resource
    LeaveGhlMapper leaveGhlMapper;

    @Resource
    ILeaveService iLeaveService;

    MapChcha mapChcha = new MapChcha();
    /**
     * 跳转到考勤首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "Leave.html";
    }

    /**
     * 跳转到添加考勤
     */
    @RequestMapping("/showSheet")
    public String LeaveAdd() {
        return PREFIX + "Leave_add.html";
    }

    /**
     * 跳转到修改考勤
     */
    @RequestMapping("/Leave_update/{LeaveId}")
    public String LeaveUpdate(@PathVariable Integer LeaveId, Model model) {
        LeaveGhl leaveGhl = leaveDao.findByid(LeaveId);
        model.addAttribute(leaveGhl);
        return PREFIX + "Leave_edit.html";
    }

    /**
     * 跳转到审批详情页面
     */
    @RequestMapping("/showleave/{LeaveId}")
    public String showLeave(@PathVariable Integer LeaveId, Model model) {
        LeaveGhl leaveGhl=null;
        //查询申请信息
        leaveGhl= (LeaveGhl) mapChcha.getValue(LeaveId);
        if (leaveGhl==null){
            leaveGhl = leaveDao.findByid(LeaveId);
        }
        model.addAttribute(leaveGhl);
        //查看审批详情
        List<DecisionLeave> decisionLeaves = leaveDao.getDecsForid(LeaveId.toString());
        model.addAttribute("decisionLeaves", decisionLeaves);

        return PREFIX + "Leave_decision_edit.html";
    }

    /**
     * 获取考勤列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String autoJSON) {
        List<LeaveGhl> list=new ArrayList<>();
        //判断缓存是否有值
        Map<Integer,LeaveGhl> map =mapChcha.getCache();
        if(map!=null){
            if (autoJSON!=null){
                List<LeaveGhl> l=mapChcha.getByname(autoJSON);
                return l;
            }
            for (Map.Entry<Integer, LeaveGhl> entry : map.entrySet()) {
                    list.add(entry.getValue());
            }
            return list;
        }

        list = leaveDao.getAllleave(autoJSON);
        try {
            mapChcha.addCache(list);
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 新增考勤
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Tip add(LeaveGhl leaveGhl, HttpServletRequest request) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date beginTime = sdf.parse(request.getParameter("beginTime"));
        leaveGhl.setStartTime(beginTime);
        Date lastTime = sdf.parse(request.getParameter("lastTime"));
        leaveGhl.setEndTime(lastTime);
        ShiroUser shiroUser = (ShiroUser) super.getSession().getAttribute("shiroUser");
        try {
            User user = leaveDao.getUserforId(shiroUser.getId().toString());
            leaveGhl.setName(shiroUser.getAccount());
            iLeaveService.addLeave(leaveGhl);
            iLeaveService.javaEmil(user.getName(),"1127730778@qq.com",leaveGhl.getRemark(),user.getEmail());

            //刷新缓存
            mapChcha.addOnecaChe(leaveGhl);

            return this.SUCCESS_TIP;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BussinessException(BizExceptionEnum.CANT_DELETE_ADMIN);
        }


    }

    /**
     * 删除考勤
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Map<String,Object> delete(HttpServletRequest request) {
        String id = request.getParameter("id");
        try {
            leaveDao.delMyslefLeaveforId(id);
            Map<String,Object> map = new HashMap<>();
            map.put("success","删除成功");
            return  map;
        }catch (Exception e){
            e.printStackTrace();
        }
        Map<String,Object> map = new HashMap<>();
        map.put("success","删除失败");
        return map;
    }


    /**
     * 修改考勤
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update() {
        return super.SUCCESS_TIP;
    }

    /**
     * 考勤详情
     */
    @RequestMapping(value = "/detail")
    @ResponseBody
    public Object detail() {
        return null;
    }

    /**
     * 跳转到自己申请的请假信息页面
     */
    @RequestMapping("/showmeleave")
    public String toShowmeLeavepage(Model model) {
        ShiroUser shiroUser = (ShiroUser) super.getSession().getAttribute("shiroUser");
        List<LeaveGhl> leaveGhls = leaveDao.getDesforName(shiroUser.getAccount());
        model.addAttribute("leaveGhls", leaveGhls);
        return PREFIX + "Leave_me_edit.html";
    }

    /**
     * 获取JSON集合用于自动补全
     */
    @RequestMapping(value = "/getjson",method = RequestMethod.POST)
    @ApiOperation(value ="json补全")
    @ResponseBody
    public List<String> getJson(String name){
      return leaveDao.getJson(name);
    }

    /**
     * 审批通过
     */
    @RequestMapping("/sayyes")
    @ResponseBody
    public Tip sayYes(String id, String context,String person){
        LeaveGhl leaveGhl = null;
        //获取登陆人的登陆信息
        ShiroUser shiroUser = (ShiroUser) super.getSession().getAttribute("shiroUser");
        //获取登陆者完整信息
        User user = leaveDao.getUserforId(shiroUser.getId().toString());
        //2级审批通过
        if (user.getLevel()==2){
        try {
            iLeaveService.adminSay(id,context,"1","(老鸟)"+person);

            //刷新缓存
            leaveGhl=leaveDao.findByid(Integer.parseInt(id));
            leaveGhl.setId(Integer.parseInt(id));
            mapChcha.addOnecaChe(leaveGhl);

            User leaveUser = leaveDao.getUserforLeaveId(id);
            iLeaveService.javaEmil(user.getName(),leaveUser.getEmail(),context + ",-----------------批准请假,祝假期愉快!",user.getEmail());
            return SUCCESS_TIP;
        } catch (Exception e) {
            e.printStackTrace();
        }
        }

        //3级审批通过
        try {
        iLeaveService.adminSay(id,context,"2","(超级管理员)"+person);

            //刷新缓存
            leaveGhl=leaveDao.findByid(Integer.parseInt(id));
            leaveGhl.setId(Integer.parseInt(id));
            mapChcha.addOnecaChe(leaveGhl);

        User leaveUser = leaveDao.getUserforLeaveId(id);
        iLeaveService.javaEmil(user.getName(), leaveUser.getEmail(), context + ",------------------------批准通过!",user.getEmail());
        return SUCCESS_TIP;
    } catch (Exception e) {
        e.printStackTrace();
    }
        throw new BussinessException(BizExceptionEnum.CANT_DELETE_ADMIN);
    }

    /**
     * 审批不通过
     */
    @RequestMapping("/sayno")
    @ResponseBody
    public Tip sayNo(String id, String context,String person){
        //获取登陆人的登陆信息
        ShiroUser shiroUser = (ShiroUser) super.getSession().getAttribute("shiroUser");
        //获取登陆者完整信息
        User user = leaveDao.getUserforId(shiroUser.getId().toString());

        //2级审批不通过
        if (user.getLevel()==2){
            try {
                iLeaveService.adminSay(id,context,"0","(老鸟)"+person);
                User leaveUser = leaveDao.getUserforLeaveId(id);
                iLeaveService.javaEmil(user.getName(),leaveUser.getEmail(),context + ",-----------------批准请假,祝假期愉快!",user.getEmail());
                return SUCCESS_TIP;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //3级审批不通过
        try {
            iLeaveService.adminSay(id,context,"1","(超级管理员)"+person);
            User leaveUser = leaveDao.getUserforLeaveId(id);
            iLeaveService.javaEmil(user.getName(), leaveUser.getEmail(), context + ",------------------------批准不通过!",user.getEmail());
            return SUCCESS_TIP;
        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new BussinessException(BizExceptionEnum.CANT_DELETE_ADMIN);
    }

    @RequestMapping("/timer")
    @ResponseBody
    public String timer(){
        try {
            iLeaveService.timer();
            return "success";
        }catch (Exception e){
            return e.getMessage();
        }
    }

    @RequestMapping("/codes")
    public void getCodes(HttpServletRequest request, HttpServletResponse response) throws IOException {
            HttpSession session = request.getSession();
            final int width = 180; // 图片宽度
            final int height = 40; // 图片高度
            final String imgType = "jpeg"; // 指定图片格式 (不是指MIME类型)
            final OutputStream output = response.getOutputStream(); // 获得可以向客户端返回图片的输出流
            String code = CodeUtil.create(width, height, imgType, output);
            session.setAttribute("code",code);
            System.out.println("验证码内容: " + code);
    }

    @RequestMapping("/yanzheng")
    @ResponseBody
    public String yanZheng(String inputcode,HttpSession session){
        String codeval= (String) session.getAttribute("code");
        if (codeval.equals(inputcode)){
            return "验证成功";
        }
        return "验证失败";
    }

    @RequestMapping("/getcodes")
    @ResponseBody
    public String getCodes(String phones,HttpSession session){
        String code=GetMessage.getResult(phones);
        session.setAttribute("phonecode",code);
        if (code!=null || code!=""){
            return "发送成功";
        }
        return "发送失败";
    }

    @RequestMapping("/phonecode")
    @ResponseBody
    public String phoneCode(String codes,HttpSession session){
        try {
            if (codes.equals(session.getAttribute("phonecode"))){
                return "验证成功";
            }
            return "验证失败";
        }catch (Exception e){
            return e.getMessage();
        }

    }

    @RequestMapping("/digui")
    public String diGui(Model model){
         List<TreeNode> list=iLeaveService.getDIgui();
         model.addAttribute("TreeList",list);
         return PREFIX+"Leave_me_edit.html";
    }
}
