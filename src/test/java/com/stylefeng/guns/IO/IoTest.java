package com.stylefeng.guns.IO;

import java.io.*;

public  class IoTest {
    public static void main(String[] args) {
        try {
            FileInputStream fileInputStream = new FileInputStream("E:\\2.xlsx");
            InputStreamReader isr = new InputStreamReader(fileInputStream,"UTF-8");
            BufferedReader bufferedReader =new BufferedReader(isr);
            String s = null;
            while ((s=bufferedReader.readLine())!=null){
                System.out.print(s);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
