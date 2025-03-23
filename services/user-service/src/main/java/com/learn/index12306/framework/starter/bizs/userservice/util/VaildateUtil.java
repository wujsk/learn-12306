package com.learn.index12306.framework.starter.bizs.userservice.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author: cyy
 * @create: 2025-03-23 20:49
 **/
public class VaildateUtil {

    // 中国大陆手机号正则表达式
    private static final String PHONE_NUMBER_REGEX = "^1[3-9]\\d{9}$";

    // 身份证号正则表达式（18 位）
    private static final String ID_CARD_REGEX = "^[1-9]\\d{5}(19|20)\\d{2}(0[1-9]|1[0-2])(0[1-9]|[12]\\d|3[01])\\d{3}[0-9Xx]$";

    // 权重系数
    private static final int[] WEIGHT_COEFFICIENTS = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};

    // 校验码对应关系
    private static final char[] CHECK_CODES = {'1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2'};

    // 邮箱正则表达式
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";

    /**
     * 校验手机号格式
     * @param phoneNumber 手机号
     * @return 如果手机号格式正确，返回 true；否则返回 false
     */
    public static boolean isValidPhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.isEmpty()) {
            return false;
        }
        Pattern pattern = Pattern.compile(PHONE_NUMBER_REGEX);
        Matcher matcher = pattern.matcher(phoneNumber);
        return matcher.matches();
    }

    /**
     * 校验身份证号格式
     * @param idCard 身份证号
     * @return 如果身份证号格式正确，返回 true；否则返回 false
     */
    public static boolean isValidIDCard(String idCard) {
        if (idCard == null || idCard.isEmpty()) {
            return false;
        }

        // 校验格式
        Pattern pattern = Pattern.compile(ID_CARD_REGEX);
        Matcher matcher = pattern.matcher(idCard);
        if (!matcher.matches()) {
            return false;
        }

        // 校验校验码
        return validateCheckCode(idCard);
    }

    /**
     * 校验身份证号的校验码
     * @param idCard 身份证号
     * @return 如果校验码正确，返回 true；否则返回 false
     */
    private static boolean validateCheckCode(String idCard) {
        char[] idCardChars = idCard.toCharArray();
        int sum = 0;

        // 计算前 17 位的加权和
        for (int i = 0; i < 17; i++) {
            sum += (idCardChars[i] - '0') * WEIGHT_COEFFICIENTS[i];
        }

        // 计算校验码
        int remainder = sum % 11;
        char expectedCheckCode = CHECK_CODES[remainder];

        // 校验校验码
        char actualCheckCode = Character.toUpperCase(idCardChars[17]);
        return expectedCheckCode == actualCheckCode;
    }
    /**
     * 校验邮箱格式
     * @param email 邮箱地址
     * @return 如果邮箱格式正确，返回 true；否则返回 false
     */
    public static boolean isValidEmail(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
