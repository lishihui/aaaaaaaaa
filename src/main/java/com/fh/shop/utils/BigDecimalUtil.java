package com.fh.shop.utils;

import java.math.BigDecimal;

public class BigDecimalUtil {
    public static BigDecimal multiplyBigDecimal(String a, String b) {
        BigDecimal bigDecimala = new BigDecimal(a);
        BigDecimal bigDecimalb = new BigDecimal(b);
        return bigDecimala.multiply(bigDecimalb).setScale(2);
    }

    public static BigDecimal addBigDecimal(String a, String b) {
        BigDecimal bigDecimala = new BigDecimal(a);
        BigDecimal bigDecimalb = new BigDecimal(b);
        return bigDecimala.add(bigDecimalb).setScale(2);
    }

    public static String getMoney(String amount) {
        if (amount == null) {
            return "";
        }
        // 金额转化为分为单位
        // 处理包含, ￥ 或者$的金额
        String currency = amount.replaceAll("\\$|\\￥|\\,", "");
        int index = currency.indexOf(".");
        int length = currency.length();
        Long amLong = 0l;
        if (index == -1) {
            amLong = Long.valueOf(currency + "00");
        } else if (length - index >= 3) {
            amLong = Long.valueOf((currency.substring(0, index + 3)).replace(".", ""));
        } else if (length - index == 2) {
            amLong = Long.valueOf((currency.substring(0, index + 2)).replace(".", "") + 0);
        } else {
            amLong = Long.valueOf((currency.substring(0, index + 1)).replace(".", "") + "00");
        }
        return amLong.toString();
    }

}
