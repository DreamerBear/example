package com.good.xuchao.example;

import org.junit.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * All rights Reserved, Designed By www.maihaoche.com
 *
 * @Package com.good.xuchao.example
 * @author: 绪超（xuchao@maihaoche.com）
 * @date: 2018/6/29 下午3:38
 * @Copyright: 2017-2020 www.maihaoche.com Inc. All rights reserved.
 * 注意：本内容仅限于卖好车内部传阅，禁止外泄以及用于其他的商业目
 */
public class TC1 {
    public static void main(String[] args) throws InterruptedException {
        Properties properties = System.getProperties();
//        properties.forEach((key,value)->{
//            System.out.println(key+"\t"+value);
//        });
        System.out.println("start--");
        Thread thread = new Thread(){
            @Override
            public void run() {
                try {
                    TimeUnit.SECONDS.sleep(5L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
        thread.join();
        System.out.println("finish--");
    }

    @Test
    public void test() {
        HashMap<String, String> hashMap = new HashMap<>();
        Hashtable<String, String> hashTable = new Hashtable<>();
        ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap();

        hashMap.put(null, null);

        try {
            hashTable.put(null, "1");
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            hashTable.put("1", null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            concurrentHashMap.put(null, "1");
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            concurrentHashMap.put("1", null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test1(){
        String tables = "chevrolet_asset_right_chglog\n" +
                "chevrolet_asset_right_chgrecord\n" +
                "chevrolet_asset_right_relation\n" +
                "chevrolet_brand\n" +
                "chevrolet_car\n" +
                "chevrolet_car_his\n" +
                "chevrolet_car_status_lock\n" +
                "chevrolet_check_list\n" +
                "chevrolet_check_resource\n" +
                "chevrolet_check_resource_conf\n" +
                "chevrolet_factory\n" +
                "chevrolet_models\n" +
                "chevrolet_series\n" +
                "chevrolet_series_image\n" +
                "chevrolet_vin_whitelist\n";

        String[] split = tables.split("\n");
        for (String table:split){
            System.out.println("ALTER TABLE "+table+"\n" +
                    "  modify COLUMN gmt_create datetime default CURRENT_TIMESTAMP not null\n" +
                    "  comment '创建时间';\n" +
                    "ALTER TABLE "+table+"\n" +
                    "  modify COLUMN gmt_modified datetime default CURRENT_TIMESTAMP not null\n" +
                    "  on update CURRENT_TIMESTAMP\n" +
                    "  comment '修改时间';");
        }

    }

    @Test
    public void test3(){
      String str = "杭州一骑轻尘信息技术有限公司-后台-产品技术部-管尚-监管";
        String substring = str.substring(str.indexOf("-")+1, str.length());
        System.out.println(substring);
    }

    @Test
    public void test4(){
        System.out.println("2018-08-31".getBytes().length);
        System.out.println("差旅费差旅费差旅费费".getBytes().length);
        System.out.println("123123".getBytes().length);

    }

    @Test
    public void test5(){
        System.out.println(new Date(1540224000000L));
    }
}
