package com.stylefeng.guns.modular.system.dao;

import com.stylefeng.guns.common.persistence.model.VoteSpy;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface VoteDao {

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
     * 投票,增加投票信息
     * @param id
     * @Param ip
     * @return int
     */
    int goVote(@Param("id") String id, @Param("ip") String ip, @Param("time") Date time);

    /**
     * 增加投票数+1
     * @param id
     * @return int
     */
    int addVotes(@Param("id") String id);

}
