package com.stylefeng.guns.Thread;


import com.stylefeng.guns.core.util.MillisUtil;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

public class ThreadDemo extends Thread {

    public static void main(String[] args) throws ParseException {
        MillisUtil millisUtil = new MillisUtil();
        List<Date> list=millisUtil.getDates("0 0/30 9-17 * * ?");
        for (Date date : list) {
            System.out.println(date);
        }

/*        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                while (true){
                    System.out.print("hello!");
                    try {
                        Thread.sleep(millisUtil.getMillis(list));
                    }catch (InterruptedException  e){
                        e.printStackTrace();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();*/
    }

}
