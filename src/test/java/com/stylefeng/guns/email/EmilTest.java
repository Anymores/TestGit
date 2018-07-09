package com.stylefeng.guns.email;


import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class EmilTest {



    @Test
    public void testSendEmail() throws ParseException {
        String string = "2016-10-24 21:59:06";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(sdf.parse(string));

    }



}
