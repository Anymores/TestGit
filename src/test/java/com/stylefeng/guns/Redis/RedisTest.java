package com.stylefeng.guns.Redis;

import com.stylefeng.guns.common.persistence.model.User;
import com.stylefeng.guns.core.shiro.ShiroUser;
import com.stylefeng.guns.core.util.SerializeUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.stream.StreamSupport;

public class RedisTest {
    public static void main(String[] args) {
       // Jedis jedis = new Jedis("localhost");
        //jedis.set("al_test","set from java");
       // System.out.println(jedis.get("al_test"));
       // byte[] bytes=jedis.get("jack".getBytes());
       // System.out.print(SerializeUtils.deSerialize(bytes));
       // byte[] bytes=jedis.get("shipy").getBytes();
        //System.out.print(SerializeUtils.deSerialize(bytes));
       // User user = new User();
       // user.setAccount("asd");
       //user.setPassword("asd");
        //jedis.set(user.getAccount().getBytes(), SerializeUtils.serialize(user));
       // byte[] bytes=jedis.get("asd".getBytes());
        //ystem.out.print(jedis.get("asd"));
       // System.out.print(bytess);
       // System.out.print(SerializeUtils.deSerialize(bytes));

        /** 连接池配置信息 **/ JedisPoolConfig config = new JedisPoolConfig();
        /** 生成连接池 **/ JedisPool pool = new JedisPool(config, "127.0.0.1", 6379);
        /** 从连接池获取连接 **/ Jedis client = pool.getResource();
        try {
            /** 执行指令 **/
            String result = client.set("MyRedis", "Hello Redis");
            System.out.println("\"set指令执行结果:%s\""+result);
           // System.out.println( String.format("set指令执行结果:%s", result) );
            String value = client.get("MyRedis");
            System.out.print("\"get指令执行结果:%s\""+value);
            //System.out.println( String.format("get指令执行结果:%s", value) );
        } catch (Exception e) {
            System.out.println("exception ......");
        }

        finally
        {
            /** 业务操作完成，将连接返回给连接池 **/
            if (null != client) {
                pool.returnBrokenResource(client);
            }
        } /** 应用关闭时，释放连接池资源 **/
        pool.destroy();
    }




    }
