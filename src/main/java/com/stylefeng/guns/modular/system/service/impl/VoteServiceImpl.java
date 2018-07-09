package com.stylefeng.guns.modular.system.service.impl;

import com.stylefeng.guns.common.persistence.model.VoteSpy;
import com.stylefeng.guns.core.util.JavaGetIpAddr;
import com.stylefeng.guns.core.util.Result;
import com.stylefeng.guns.modular.system.dao.VoteDao;
import com.stylefeng.guns.modular.system.service.IVoteService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * 投票Dao
 *
 * @author fengshuonan
 * @Date 2018-06-28 09:13:23
 */
@Service
public class VoteServiceImpl implements IVoteService {

    @Resource
    VoteDao voteDao;


    @Override
    public List<VoteSpy> getAllvote() {
        return voteDao.getAllvote();
    }

    @Override
    public List<String> getAllip() {
        return voteDao.getAllip();
    }

    @Override
    @Transactional
    public Object doVote(HttpServletRequest request, String id) {
        try {
            if (!voteYesorNo(request)){
                String ip = getIpAdders(request);
                voteDao.addVotes(id);
                voteDao.goVote(id,ip,new Date());
                return Result.toClient(true,"投票成功");
            }
        }catch (Exception e){
            e.printStackTrace();
            return Result.toClient(false,"投票失败");
        }
        return Result.toClient(false,"投票失败,已经投票!");
    }

    @Override
    public boolean lookVote(HttpServletRequest request) {
        boolean flag=false;
        if(voteYesorNo(request)){
            flag=true;
        }
        return flag;
    }

    /**
     * 获取操作端IP
     */
    public String getIpAdders(HttpServletRequest request){
        JavaGetIpAddr javaGetIpAddr = new JavaGetIpAddr();
        return javaGetIpAddr.getIpAddr(request);
    }

    /**
     * 判断有没有重复投票的Ip地址 返回boolean类型,true为重复,false无重复
     * @return boolean
     */
    public boolean voteYesorNo(HttpServletRequest request){
        boolean flag=false;
        List<String> allIp=voteDao.getAllip();
        //用于判断有没有重复的标识符
        String ip = getIpAdders(request);
        int i=0;
        for (String s : allIp) {
            if (s.equals(ip)){
                i++;
            }
        }
        if (i>0){
            flag=true;
        }
        return flag;
    }


}
