package com.fh.shop.utils;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class IDUtil {

    public static String createId() {
        DateTimeFormatter yyyyMMddHHmm = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
        return LocalDateTime.now().format(yyyyMMddHHmm) + IdWorker.getId();
    }
}
