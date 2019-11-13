package com.ginko.driver.api.md5;

import org.springframework.util.DigestUtils;

public class Md5Util {

    //盐，用于混交md5
    private static final String slat = "&%5123***&&%%$$#@";
    /**
     * 生成md5
     * @param seckillId
     * @return
     */
    public static String getMD5(String str) {
        String base = str +"/"+slat;
        String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
        md5 = md5.substring(8, 24);//输出16位16进制字符串
        return md5;
    }


    public static void main(String[] args) {
        String x = getMD5("123123aa");
        System.out.println(x.length());
    }
}
