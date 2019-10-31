package com.fh.shop.utils;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class MyQuartzsTest {

    @Scheduled(cron = "*/5 * * * * ?")//每隔5秒执行一次
    public void test() throws Exception {
        /* System.out.println("This  is dog.....");*/
    }

}
