package com.stylefeng.guns.modular.system.controller;

import com.stylefeng.guns.common.persistence.model.VoteSpy;
import com.stylefeng.guns.modular.system.dao.VoteDao;
import com.stylefeng.guns.modular.system.service.IVoteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/Vote")
public class VoteController {

    @Resource
    VoteDao voteDao;

    @Resource
    IVoteService iVoteService;

    private String PREFIX = "/system/Vote/";

    @RequestMapping("")
    public String index(Model model) {
        model.addAttribute("votelist",voteDao.getAllvote());
        return PREFIX + "Vote.html";
    }

    @RequestMapping("/getvotes")
    @ResponseBody
    public List<VoteSpy> getVotes(){
        return voteDao.getAllvote();
    }

    @RequestMapping("/tovotedet")
    public String toVotedet(){
        return PREFIX + "Vote_det.html";
    }

    @RequestMapping("/lookvote")
    @ResponseBody
    public Object lookVote(HttpServletRequest request){
        if (iVoteService.lookVote(request)){
            return true;
        }
        return false;
    }
    /**
     * 投票操作
     * @param id
     * @return
     */
    @RequestMapping("/dovote")
    @ResponseBody
    public Object doVote(String id,HttpServletRequest request) {
        return iVoteService.doVote(request,id);
    }






}
