package com.eress.bootmaven.common;

import org.apache.commons.codec.digest.DigestUtils;

import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordUtil {
    /**
     * 0 PW_AVAILABLE: 사용가능한 패스워드
     * 9 NULL_PARAM: 입력값이 없음
     * 1 SERVER_ERROR: 에러
     * 2 PW_INVALID_SHORT: 패스워드의 길이가 너무 짧음
     * 3 PW_SIMILAR_ID: 패스워드가 아이디와 유사함
     * 4 PW_INVALID_COMPOSE: 영문, 숫자, 특수문자 등을 두개이상 혼합한 패스워드가 아님
     * 5 PW_INVALID_CONSECUTIVE: 패스워드가 순차적임 (예, abc, def, 123)
     * 5 PW_INVALID_CONSECUTIVE: 패스워드가 순차적임 (예, aaa, 222)
     * 5 PW_INVALID_CONSECUTIVE: 패스워드가 순차적임 (예, asd, qwe, jkl)
     */
    public static int checkPassword(String id, String pw) {
        int result = 1;
        if (pw == null || "".equals(pw)) {
            return 9;
        }
        if (id != null && !"".equals(id)) {
            if (pw.contains(id)) {
                return 3;
            }
        }
        try {
            Pattern alphabetLow = Pattern.compile("[a-z]");             // 영소문자
            Pattern alphabetUp = Pattern.compile("[A-Z]");              // 영대문자
            Pattern number = Pattern.compile("[0-9]");                  // 숫자
            Pattern specialChar = Pattern.compile("\\p{Punct}");        // 특수문자 -_=+\\|()*&^%$#@!~`?></;,.:'
            Pattern threeChar = Pattern.compile("(\\p{Alnum})\\1{2,}"); // 3자리 이상 같은 문자 또는 숫자
            Matcher match;
            int charType = 0;

            // 영소문자가 포함되어 있는가?
            match = alphabetLow.matcher(pw);
            if (match.find()) {
                charType++;
            }
            // 영대문자가 포함되어 있는가?
            match = alphabetUp.matcher(pw);
            if (match.find()) {
                charType++;
            }
            // 숫자가 포함되어 있는가?
            match = number.matcher(pw);
            if (match.find()) {
                charType++;
            }
            // 특수문자가 포함되어 있는가?
            match = specialChar.matcher(pw);
            if (match.find()) {
                charType++;
            }
            // 3자리 이상 같은 문자 또는 숫자가 포함되어 있는가?
            match = threeChar.matcher(pw);
            if (match.find()) {
                return 5;
            }
            // 3가지 이상 조합인가?
            if (charType >= 3) {
                if (pw.length() < 8) {
                    return 2;
                } else {
                    result = 0;
                }
                // 2가지 조합인가?
            } else if (charType == 2) {
                if(pw.length() < 10) {
                    return 2;
                } else {
                    result = 0;
                }
                // 1가지 조합인가?
            } else {
                return 4;
            }
            // 연속된 3자리 이상의 문자나 숫자가 포함되어 있는가?
            String listThreeChar = "abc|bcd|cde|def|efg|fgh|ghi|hij|ijk|jkl|klm|lmn|mno|nop|opq|qrs|rst|stu|tuv|uvw|vwx|wxy|xyz|012|123|234|345|456|567|678|789|890";
            String[] arrThreeChar = listThreeChar.split("\\|");
            for (String s : arrThreeChar) {
                if (pw.toLowerCase().matches(".*" + s + ".*")) {
                    return 5;
                }
            }
            // 연속된 3자리 이상의 키보드 문자가 포함되어 있는가?
            String listKeyboardThreeChar = "qwe|wer|ert|rty|tyu|yui|uio|iop|asd|sdf|dfg|fgh|ghj|hjk|jkl|zxc|xcv|cvb|vbn|bnm";
            String[] arrKeyboardThreeChar = listKeyboardThreeChar.split("\\|");
            for (String s : arrKeyboardThreeChar) {
                if (pw.toLowerCase().matches(".*" + s + ".*")) {
                    return 5;
                }
            }
        } catch (Exception ex) {
            result = 1;
        }
        return result;
    }

    public static boolean matchPassword(String pw, String encPw) {
        return DigestUtils.sha256Hex(pw).equals(encPw);
    }

    /**
     * 10자리 임시비밀번호 생성
     */
    public static String randomPassword() {
        String uuid = String.valueOf(UUID.randomUUID()).replaceAll("-", "");
        return uuid.substring(0, 10);
    }

    public static String randomWord(int len) {
        String uuid = String.valueOf(UUID.randomUUID()).replaceAll("-", "");
        return uuid.substring(0, len);
    }

    /**
     * SMS 인증번호 생성 (관리자 로그인 등)
     */
    public static String createCertNumber() {
        Random random = new Random();
        String temp = Integer.toString(random.nextInt()).trim();
        return temp.substring( temp.length()-6);
    }
}
