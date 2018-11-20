package com.good.xuchao.example.utils;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static com.good.xuchao.example.utils.CollectionTransferUtils.*;

/**
 * All rights Reserved, Designed By www.maihaoche.com
 *
 * @Package com.good.xuchao.example.utils
 * @author: 绪超（xuchao@maihaoche.com）
 * @date: 2018/9/14 下午3:09
 * @Copyright: 2017-2020 www.maihaoche.com Inc. All rights reserved.
 * 注意：本内容仅限于卖好车内部传阅，禁止外泄以及用于其他的商业目
 */
public class CollectionTransferUtilsTest {
    @Test
    public void test() {
        List<Boy> input = Lists.newArrayList();
        for (int i = 0; i < 10; i++) {
            input.add(Boy.randomInstance(2));
        }
        input.add(new Boy("二哈", null));
        System.out.println(input);
        System.out.println(transferToList(input, Boy::getAge, CollectionTransferUtils.TransferFeature.DISTINCT, CollectionTransferUtils.TransferFeature.FILTER_NONNULL));
        System.out.println(transferToNonNullDistinctList(input, Boy::getAge));
        System.out.println(transferToMap(input, Boy::getName));
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static class Boy {
        String name;
        Integer age;

        static Boy randomInstance(Integer maxAge) {
            return new Boy(new String(new char[]{
                    (char) (ThreadLocalRandom.current().nextInt(20962) + 19968),
                    (char) (ThreadLocalRandom.current().nextInt(20962) + 19968)}
            )
                    , ThreadLocalRandom.current().nextInt(maxAge)
            );
        }

        @Override
        public String toString() {
            return String.format("[name:%s, age:%s]", name, age);
        }
    }

    public static void main(String[] args) {

    }
}
