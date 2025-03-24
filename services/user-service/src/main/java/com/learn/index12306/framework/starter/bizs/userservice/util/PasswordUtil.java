package com.learn.index12306.framework.starter.bizs.userservice.util;

import cn.hutool.crypto.digest.MD5;

/**
 * @author: cyy
 * @create: 2025-03-24 10:11
 **/
public class PasswordUtil {

    private static final MD5 MD5 = new MD5();

    private static final String SLAT = "42bb11eb28e2d11325dea02d5225f493";

    public static String encrypt(String password) {
        return MD5.digestHex(password + SLAT);
    }

    public static boolean match(String password, String encryptPassword) {
        return encrypt(password).equals(encryptPassword);
    }

    public static void main(String[] args) {
        System.out.println(MD5.digestHex("12305-越来越好"));
    }
}
