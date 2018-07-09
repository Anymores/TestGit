package com.stylefeng.guns.core.util;

import org.quartz.CronExpression;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MillisUtil {

    public static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
    public static SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");

    public List<Date> getDates(String cron) throws ParseException {
        Date nextTime = df.parse(df2.format(new Date()) + " 00:00:00");
        Date to = new Date(nextTime.getTime() + 24*3600*1000);
        CronExpression expression;
        List<Date> crontimes = new ArrayList<>();
        expression = new CronExpression(cron);
        for(;nextTime.getTime()<=to.getTime();){
            nextTime = expression.getNextValidTimeAfter(nextTime);
            if(nextTime.getTime()>=to.getTime()) break;
            crontimes.add(nextTime);
            if (crontimes.size()==2) return crontimes;
        }
        return null;
    }

    public long getMillis(List<Date> times) throws ParseException {
        Calendar c = Calendar.getInstance();
        List<Long> list = new ArrayList();
        for (Date time : times) {
            c.setTime(df.parse(df.format(time)));
            list.add(c.getTimeInMillis());
        }
        return list.get(1)-list.get(0);
    }
}
