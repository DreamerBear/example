package com.good.xuchao.example.utils;

import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * All rights Reserved, Designed By www.maihaoche.com
 *
 * @Package com.good.xuchao.example.utils
 * @author: 绪超（xuchao@maihaoche.com）
 * @date: 2018/9/14 下午2:08
 * @Copyright: 2017-2020 www.maihaoche.com Inc. All rights reserved.
 * 注意：本内容仅限于卖好车内部传阅，禁止外泄以及用于其他的商业目
 */
public class CollectionTransferUtils {

    public enum TransferFeature {
        FILTER_NONNULL,
        DISTINCT
    }

    private CollectionTransferUtils() {
        throw new AssertionError("no instance for you~");
    }

    public static <T, R> List<R> transferToList(List<T> source, Function<T, R> mapper, TransferFeature... transferFeatures) {
        if (Objects.isNull(source) || source.size() <= 0) {
            return new ArrayList<>();
        }
        Stream<R> stream = source.stream().map(mapper);
        if (transferFeatures != null && transferFeatures.length > 0) {
            Set<TransferFeature> featureSet = Arrays.stream(transferFeatures).collect(Collectors.toSet());
            if (featureSet.contains(TransferFeature.FILTER_NONNULL)) {
                stream = stream.filter(Objects::nonNull);
            }
            if (featureSet.contains(TransferFeature.DISTINCT)) {
                stream = stream.distinct();
            }
        }
        return stream.collect(Collectors.toList());
    }

    public static <T, R> List<R> transferToNonNullDistinctList(List<T> source, Function<T, R> mapper) {
        if (Objects.isNull(source) || source.size() <= 0) {
            return new ArrayList<>();
        }
        return source.stream().map(mapper).filter(Objects::nonNull).distinct().collect(Collectors.toList());
    }

    public static <T, K, U> Map<K, U> transferToMap(List<T> source, Function<T, K> keyMapper, Function<T, U> valueMapper, BinaryOperator<U> mergeFunction) {
        if (Objects.isNull(source) || source.size() <= 0) {
            return new HashMap<>();
        }
        return source.stream().collect(Collectors.toMap(keyMapper, valueMapper, mergeFunction));
    }

    public static <T, K, U> Map<K, U> transferToMap(List<T> source, Function<T, K> keyMapper, Function<T, U> valueMapper) {
        if (Objects.isNull(source) || source.size() <= 0) {
            return new HashMap<>();
        }
        return source.stream().collect(Collectors.toMap(keyMapper, valueMapper, (oldV, newV) -> oldV));
    }

    public static <T, K> Map<K, T> transferToMap(List<T> source, Function<T, K> keyMapper) {
        if (Objects.isNull(source) || source.size() <= 0) {
            return new HashMap<>();
        }
        return source.stream().collect(Collectors.toMap(keyMapper, v -> v, (oldV, newV) -> oldV));
    }


}
