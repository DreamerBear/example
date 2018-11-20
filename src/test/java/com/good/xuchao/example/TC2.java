package com.good.xuchao.example;

import com.google.common.collect.Lists;
import lombok.Data;
import org.apache.commons.collections.CollectionUtils;
import org.junit.Test;
import org.markdownj.MarkdownProcessor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

/**
 * All rights Reserved, Designed By www.maihaoche.com
 *
 * @Package com.good.xuchao.example
 * @author: 绪超（xuchao@maihaoche.com）
 * @date: 2018/7/18 下午4:51
 * @Copyright: 2017-2020 www.maihaoche.com Inc. All rights reserved.
 * 注意：本内容仅限于卖好车内部传阅，禁止外泄以及用于其他的商业目
 */
public class TC2 {
    public static void daysBetween(String first, String second) throws ParseException {
        SimpleDateFormat sformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        daysBetween(sformat.parse(first), sformat.parse(second));
    }

    /**
     * 计算两个日期之间相差的天、时、分、秒
     *
     * @return 相差天数
     * @throws ParseException
     */
    public static void daysBetween(Date first, Date second) throws ParseException {
        SimpleDateFormat sformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        first = sformat.parse(sformat.format(first));
        second = sformat.parse(sformat.format(second));
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(first);
        long firstMills = calendar.getTimeInMillis();
        calendar.setTime(second);
        long secondMills = calendar.getTimeInMillis();
        long rateD = 1000 * 60 * 60 * 24;
        long rateH = 1000 * 60 * 60;
        long rateM = 1000 * 60;
        long rateS = 1000;
        long mills = secondMills - firstMills;
        long days = mills / rateD;
        long hours = (mills % rateD) / rateH;
        long minutes = (mills % rateD % rateH) / rateM;
        long seconds = (mills % rateD % rateH % rateM) / rateS;
        System.out.println("时间1:" + sformat.format(first));
        System.out.println("时间2:" + sformat.format(second));
        System.out.println("两者相差:" + days + "天" + hours + "时" + minutes + "分" + seconds + "秒");
    }

    public static void main(String[] args) {

        List<Integer> a = Lists.newArrayList(1, 3, 4, 5);
        List<Integer> b = Lists.newArrayList(1, 2, 4);

        System.out.println(CollectionUtils.subtract(a, b));
    }

    public static void ii(AtomicBoolean hasProcessSpeedReturn) {
        hasProcessSpeedReturn.set(true);
    }

    @Test
    public void test() {
        MarkdownProcessor m = new MarkdownProcessor();
        String markdown = m.markdown("12312313");
        System.out.println(markdown);
    }

    /**
     * 一条包含字母 A-Z 的消息通过以下方式进行了编码：
     * <p>
     * 'A' -> 1
     * 'B' -> 2
     * ...
     * 'Z' -> 26
     * 给定一个只包含数字的非空字符串，请计算解码方法的总数。
     */
    @Test
    public void test1() {
        System.out.println(calcWays("220123112313112313"));
    }

    public int calcWays(String s) {
        if (s.startsWith("0")) {
            return 0;
        }
        int connectNum = 0;
        int ways = 1;
        for (int i = 0; i < s.length(); i++) {
            Integer intC = Integer.valueOf(String.valueOf(s.charAt(i)));
            if (1 <= intC && intC <= 2) {
                connectNum++;
            } else {
                if (1 <= intC && intC <= 6) {
                    connectNum++;
                }else if(intC!=0 && i-1>=0 && (Integer.valueOf(String.valueOf(s.charAt(i - 1))) == 1)){
                    connectNum++;
                }
                if (i + 1 < s.length() && Integer.valueOf(String.valueOf(s.charAt(i + 1))) == 0) {
                    connectNum--;
                }
                if (intC == 0) {
                    if(i-1>=0 && (Integer.valueOf(String.valueOf(s.charAt(i - 1))) >2 || Integer.valueOf(String.valueOf(s.charAt(i - 1))) ==0)){
                        return 0;
                    }
                    connectNum--;
                }
                ways *= fibonaqi(connectNum);
                connectNum = 0;
            }
        }
        if (Integer.valueOf(String.valueOf(s.charAt(s.length() - 1))) == 0) {
            connectNum--;
        }
        ways *= fibonaqi(connectNum);
        return ways;
    }

    public int fibonaqi(int i) {
        if (i <= 1) {
            return 1;
        }
        if (i == 2) {
            return 2;
        }
        return fibonaqi(i - 1) + fibonaqi(i - 2);
    }
}
