package com.good.xuchao.example;

import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.*;

/**
 * All rights Reserved, Designed By www.maihaoche.com
 *
 * @Package com.good.xuchao.example
 * @author: 绪超（xuchao@maihaoche.com）
 * @date: 2018/11/12 4:51 PM
 * @Copyright: 2017-2020 www.maihaoche.com Inc. All rights reserved.
 * 注意：本内容仅限于卖好车内部传阅，禁止外泄以及用于其他的商业目
 */
public class TC3 {

    public static final ExecutorService POOL = Executors.newFixedThreadPool(2);
    @Test
    public void test() throws ExecutionException, InterruptedException {
        CompletableFuture<String> cf1 = CompletableFuture.supplyAsync(()->{
            try {
                Thread.sleep(2000L);
                throw new RuntimeException("1e");
            } catch (InterruptedException e) {
            }
            return "1";
        },POOL);

        CompletableFuture<String> cf2 = CompletableFuture.supplyAsync(()->{
            try {
                Thread.sleep(3000L);
                throw new RuntimeException("2e");
            } catch (InterruptedException e) {
            }
            return "2";
        },POOL);

        try {

            CompletableFuture<Void> voidCompletableFuture = CompletableFuture.allOf(Lists.newArrayList(cf1,cf2).toArray(new CompletableFuture[2]));
            voidCompletableFuture.join();
            System.out.println(cf1.get());
            System.out.println(cf2.get());

        }catch (Exception e){
            e.printStackTrace();

        }

    }
}
