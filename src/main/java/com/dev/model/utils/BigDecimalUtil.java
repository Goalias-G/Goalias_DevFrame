package com.dev.model.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;


public class BigDecimalUtil {

    public static DecimalFormat fnum = new DecimalFormat("##0.00");

    /**
     * 格式化金额
     *
     * @param valueStr
     * @return String
     */
    public static String formatStr(String valueStr) {
        if (valueStr == null || valueStr == "") {
            valueStr = "0.00";
        }
        return fnum.format(new BigDecimal(valueStr));
    }


    /**
     * 金额相加
     *
     * @param valueStr 基础值
     * @param addStr   被加数
     * @return String
     */
    public static String StrAdd(String valueStr, String addStr) {
        BigDecimal value = new BigDecimal(valueStr);
        BigDecimal augend = new BigDecimal(addStr);
        return fnum.format(value.add(augend));
    }

    /**
     * 金额相加
     *
     * @param valueStr      基础值
     * @param minusValueStr 被加数
     * @return BigDecimal
     */
    public static BigDecimal add(BigDecimal valueStr, BigDecimal minusValueStr) {
        return valueStr.add(minusValueStr);
    }

    /**
     * 金额相减
     *
     * @param valueStr      基础值
     * @param minusValueStr 减数
     * @return String
     */
    public static String StrSub(String valueStr, String minusValueStr) {
        BigDecimal value = new BigDecimal(valueStr);
        BigDecimal subtrahend = new BigDecimal(minusValueStr);
        return fnum.format(value.subtract(subtrahend));
    }

    /**
     * 金额相减
     *
     * @param value      基础值
     * @param subtrahend 减数
     * @return BigDecimal
     */
    public static BigDecimal sub(BigDecimal value, BigDecimal subtrahend) {
        return new BigDecimal(fnum.format(value.subtract(subtrahend)));
    }


    /**
     * 金额相乘
     *
     * @param valueStr      基础值
     * @param minusValueStr 被乘数
     * @return String
     */
    public static String StrMul(String valueStr, String minusValueStr) {
        BigDecimal value = new BigDecimal(valueStr);
        BigDecimal mulValue = new BigDecimal(minusValueStr);
        return fnum.format(value.multiply(mulValue));
    }

    /**
     * 金额相乘
     *
     * @param value    基础值
     * @param mulValue 被乘数
     * @return BigDecimal
     */
    public static BigDecimal mul(BigDecimal value, BigDecimal mulValue) {
        return new BigDecimal(fnum.format(value.multiply(mulValue)));
    }

    /**
     * 金额相除 <br/>
     * 精确小位小数
     *
     * @param valueStr      基础值
     * @param minusValueStr 被乘数
     * @return String
     */
    public static String StrDiv(String valueStr, String minusValueStr) {
        BigDecimal value = new BigDecimal(valueStr);
        BigDecimal divideValue = new BigDecimal(minusValueStr);
        return fnum.format(value.divide(divideValue, 2, RoundingMode.HALF_UP));
    }

    /**
     * 金额相除 <br/>
     * 精确小位小数
     *
     * @param value       基础值
     * @param divideValue 被乘数
     * @return BigDecimal
     */
    public static BigDecimal div(BigDecimal value, BigDecimal divideValue) {
        return new BigDecimal(fnum.format(value.divide(divideValue, 2, RoundingMode.HALF_UP)));
    }


    /**
     * 值比较大小
     * <br/>如果valueStr大于等于compValueStr,则返回true,否则返回false
     * true 代表可用余额不足
     *
     * @param valueStr     (需要消费金额)
     * @param compValueStr (可使用金额)
     * @return boolean
     */
    public static boolean StrComp(String valueStr, String compValueStr) {
        BigDecimal value = new BigDecimal(valueStr);
        BigDecimal compValue = new BigDecimal(compValueStr);
        //0:等于    >0:大于    <0:小于
        return comp(value, compValue);
    }

    /**
     * 值比较大小
     * <br/>如果valueStr大于等于compValueStr,则返回true,否则返回false
     * true 代表可用余额不足
     *
     * @param valueStr     (需要消费金额)
     * @param compValueStr (可使用金额)
     * @return boolean
     */
    public static boolean comp(BigDecimal valueStr, BigDecimal compValueStr) {
        //0:等于    >0:大于    <0:小于
        int result = valueStr.compareTo(compValueStr);
        if (result >= 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 金额乘以，省去小数点
     *
     * @param valueStr 基础值
     * @return String
     */
    public static String mulOfNotPoint(String valueStr, String divideStr) {
        BigDecimal value = new BigDecimal(valueStr);
        BigDecimal mulValue = new BigDecimal(divideStr);
        valueStr = fnum.format(value.multiply(mulValue));
        return fnum.format(value.multiply(mulValue)).substring(0, valueStr.length() - 3);
    }

    /**
     * 给金额加逗号切割
     *
     * @param str
     * @return String
     */
    public static String addComma(String str) {
        try {
            String banNum = "";
            if (str.contains(".")) {
                String[] arr = str.split("\\.");
                if (arr.length == 2) {
                    str = arr[0];
                    banNum = "." + arr[1];
                }
            }
            // 将传进数字反转
            String reverseStr = new StringBuilder(str).reverse().toString();
            String strTemp = "";
            for (int i = 0; i < reverseStr.length(); i++) {
                if (i * 3 + 3 > reverseStr.length()) {
                    strTemp += reverseStr.substring(i * 3, reverseStr.length());
                    break;
                }
                strTemp += reverseStr.substring(i * 3, i * 3 + 3) + ",";
            }
            // 将[789,456,] 中最后一个[,]去除
            if (strTemp.endsWith(",")) {
                strTemp = strTemp.substring(0, strTemp.length() - 1);
            }
            // 将数字重新反转
            String resultStr = new StringBuilder(strTemp).reverse().toString();
            resultStr += banNum;
            return resultStr;
        } catch (Exception e) {
            return str;
        }

    }

}
