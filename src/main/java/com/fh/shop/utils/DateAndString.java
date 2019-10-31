package com.fh.shop.utils;

import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateAndString {
    public static final String Y_M_D = "yyyy-MM-dd";
    public static final String Y_M_D_H_M = "yyyy-MM-dd HH:mm";
    public static final String Y_M_D_H_M_S = "yyyy-MM-dd HH:mm:ss";

    public static String date2String(Date d, String s) {
        if (d == null) {
            return "";
        }
        SimpleDateFormat sim = new SimpleDateFormat(s);
        String format = sim.format(d);
        return format;
    }

    public static Date string2Date(String str, String s) {
        if (StringUtils.isEmpty(str)) {
            return null;
        }
        SimpleDateFormat sim = new SimpleDateFormat(s);
        Date parse = null;
        try {
            parse = sim.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return parse;
    }
}
