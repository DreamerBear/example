package com.good.xuchao.example;

import lombok.Data;
import org.junit.Before;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * All rights Reserved, Designed By www.maihaoche.com
 *
 * @Package com.good.xuchao.example
 * @author: 绪超（xuchao@maihaoche.com）
 * @date: 2019/1/3 6:34 PM
 * @Copyright: 2017-2020 www.maihaoche.com Inc. All rights reserved.
 * 注意：本内容仅限于卖好车内部传阅，禁止外泄以及用于其他的商业目
 */
public class TC4 {
    public static String getLastDayOfMonth() {
        //获取上一个月的最后一天：
        Calendar cale = Calendar.getInstance();
        cale.set(Calendar.DAY_OF_MONTH, 0);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String lastDay = format.format(cale.getTime());
        lastDay = lastDay.concat(" 23:59:59");
        return lastDay;
    }

    public static void main(String[] args) {
        System.out.println(getLastDayOfMonth());
    }

    private List<String> list = new ArrayList<>();

    List<Users> users;

    @Data
    private class Users {

        private int id;
        private String name;
        private int age;

        public Users(int id, String name, int age) {
            this.id = id;
            this.name = name;
            this.age = age;
        }
    }

    @Before
    public void load() {
        list.add("张三");
        list.add("李四");
        list.add("王五");
        list.add("赵六");

        users = Arrays.asList(new Users(1, "张三", 18),
                new Users(2, "李四", 30),
                new Users(3, "王五", 20),
                new Users(4, "赵六", 18));

    }

    @Test
    public void groupingByMax() {

        //分组后去最大值
        Map<Integer, Optional<Users>> collect = users.stream().collect(Collectors.groupingBy(Users::getAge, Collectors.maxBy(Comparator.comparing(Users::getId))));

        //分组后去最小值
        Map<Integer, Optional<Users>> collect2 = users.stream().collect(Collectors.groupingBy(Users::getAge, Collectors.minBy(Comparator.comparing(Users::getId))));

        collect.forEach((key, value) -> {
            System.out.println("key " + key + "          " + "value : " + value);
        });


        System.out.println("---------------------");

        collect2.forEach((key, value) -> {
            System.out.println("key " + key + "          " + "value : " + value);
        });

    }

    @Test
    public void mapping() {

        Map<Integer, List<String>> collect = users.stream().collect(Collectors.groupingBy(Users::getAge, Collectors.mapping( item ->{

            //当然你这里也可以构建一个新的对象，进行返回
            return item.getName();
        }, Collectors.toList())));

        //   Map<Integer, List<Object>> collect = users.stream().collect(Collectors.groupingBy(Users::getAge, Collectors.mapping(item ->{ return Arrays.asList(item); }, Collectors.toList())));

        collect.forEach((key, value) -> {
            System.out.println("key : " + key + "value :" + value);
        });
    }

    /**
     * 单字段进行排序
     */
    @Test
    public void sort() {

        //倒序
        System.out.println("倒序");
        users.stream().sorted(Comparator.comparing(Users::getAge).reversed()).collect(Collectors.toList()).forEach(item -> {
            System.out.println(item.getAge());
        });

        System.out.println("升序");

        //升序
        users.stream().sorted(Comparator.comparing(Users::getAge)).collect(Collectors.toList()).forEach(item -> {
            System.out.println(item.getAge());
        });

    }

    @Test
    public void comparator() {

        /**
         *
         * 手动控制排序（复杂业务的时候，可以采用该种方法）
         * 1、年龄倒叙
         * 2、id 升序
         *
         */
        Comparator<Users> usersComparator = Comparator.comparing(Users::getAge, (o1, o2) -> {
            //  倒叙
            return o2.compareTo(o1);
        }).thenComparing(Users::getId, (o1, o2) -> {
            // 升序
            return o1.compareTo(o2);
        });
        List<Users> collect = users.stream().sorted(usersComparator).collect(Collectors.toList());

        collect.forEach(item -> {
            System.out.println(item);
        });

        System.out.println("----------------------------------");

        /**
         *
         * 第二种排倒序方法
         * Comparator.reverseOrder 倒叙
         * Comparator.naturalOrder 升叙
         *
         * 1、年龄倒叙
         * 2、id 倒序
         *
         */
        Comparator<Users> usersComparator2 = Comparator.comparing(Users::getAge, Comparator.reverseOrder()).thenComparing(Users::getId,Comparator.reverseOrder());
        List<Users> collect2 = users.stream().sorted(usersComparator2).collect(Collectors.toList());

        collect2.forEach(item -> {
            System.out.println(item);
        });

        if(true){
            if(true){
                if(true){
                    if(true){
                        //do...
                    }
                    //
                }
                //
            }
            //
        }

    }

}
