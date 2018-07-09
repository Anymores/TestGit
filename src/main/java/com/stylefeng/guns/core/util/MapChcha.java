package com.stylefeng.guns.core.util;


import com.stylefeng.guns.common.persistence.model.LeaveGhl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapChcha {
    private  Map<Integer,LeaveGhl> cache = null;

    public Map<Integer, LeaveGhl> getCache() {
        return cache;
    }

    public void setCache(Map<Integer, LeaveGhl> cache) {
        this.cache = cache;
    }

    /**
     * 存缓存
     * @param leaveGhls
     * @return boolean
     */
    public boolean addCache(List<LeaveGhl> leaveGhls){
        if (cache==null){
            cache=new HashMap<>();
            try {
                for (LeaveGhl leaveGhl : leaveGhls) {
                    cache.put(leaveGhl.getId(),leaveGhl);
                }
                return true;
            }catch (Exception e){
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    /**
     * 增加单个缓存
     * @param leaveGhl
     * @return
     */
    public boolean addOnecaChe(LeaveGhl leaveGhl){
        try {
            cache.put(leaveGhl.getId(),leaveGhl);
            return true;
        }catch (Exception e){
            e.printStackTrace();

        }
        return false;
    }

    /**
     * 获取缓存
     * @param key
     * @return Object
     */
    public Object getValue(Integer key){
        Object ob = cache.get(key);
        if (ob!=null){
            return ob;
        }
        return null;
    }

    /**
     * 根据姓名查询
     * @param name
     * @return
     */
    public List<LeaveGhl> getByname(String name){
        List<LeaveGhl> list = new ArrayList<>();
        if(cache.size() == 0){

        }
        LeaveGhl leaveGhl;
        for (Map.Entry<Integer, LeaveGhl> entry : cache.entrySet()) {
            if (entry.getValue().getName().contains(name)) {
                leaveGhl = entry.getValue();
                list.add(leaveGhl);
            }
        }
        return list;
    }
    /**
     * 清楚缓存
     */
    public boolean clearChache(){
        try {
            cache.clear();
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

    }
}
