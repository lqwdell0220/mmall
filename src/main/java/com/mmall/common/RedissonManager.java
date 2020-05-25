package com.mmall.common;

import com.mmall.util.PropertiesUtil;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @Discription
 * @Author:liqiwen
 * @Date:2020/5/25 19:30
 */
@Component
@Slf4j
public class RedissonManager {

    private static String redis1Ip = PropertiesUtil.getProperty("redis1.ip");
    private static Integer redis1Port = Integer.parseInt(PropertiesUtil.getProperty("redis1.port"));
    private static String redis2Ip = PropertiesUtil.getProperty("redis2.ip");
    private static Integer redis2Port = Integer.parseInt(PropertiesUtil.getProperty("redis2.port"));

    private Config config = new Config();
    private Redisson redisson;

    public Redisson getRedission() {
        return redisson;
    }

    @PostConstruct
    private void init(){
        try {
            config.useSingleServer().setAddress(redis1Ip+":"+redis1Port);
            redisson = (Redisson) Redisson.create(config);
            log.info("Redisson初始化结束");
        } catch (Exception e) {
            log.error("Redisson初始化错误",e);
        }
    }
}
