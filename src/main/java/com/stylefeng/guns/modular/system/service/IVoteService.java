package com.stylefeng.guns.modular.system.service;

import com.stylefeng.guns.common.persistence.model.VoteSpy;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 投票Service
 *
 * @author fengshuonan
 * @Date 2018-06-28 09:13:23
 */
public interface IVoteService {

    /**
     * 获取所有投票信息
     * @return VoteSpy
     */
    List<VoteSpy> getAllvote();

    /**
     * 获取所有投票的IP地址
     * @return String
     */
    List<String> getAllip();

    /**
     * 投票操作
     * @param request
     * @param id
     * @return Object
     */
    Object doVote(HttpServletRequest request, String id);

    /**
     * 查看投票详情
     * @param request
     * @return boolean
     */
    boolean lookVote(HttpServletRequest request);

}
