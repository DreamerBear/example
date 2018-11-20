package com.good.xuchao.example.form;

import com.google.common.collect.Lists;
import lombok.Data;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

/**
 * All rights Reserved, Designed By www.maihaoche.com
 *
 * @Package com.good.xuchao.example.form
 * @author: 绪超（xuchao@maihaoche.com）
 * @date: 2018/9/26 上午11:13
 * @Copyright: 2017-2020 www.maihaoche.com Inc. All rights reserved.
 * 注意：本内容仅限于卖好车内部传阅，禁止外泄以及用于其他的商业目
 */
@Data
public class B<T> extends A {
    private String attrB;

    private List<C> clist = Lists.newArrayList(new C(), new C());

    private T t;

    public B() {
        attrB = "123B";
        super.setAttrA("223A");
    }

    private static final String SEPARATOR = ".";

    public static void main(String[] args) {
        B<C> b = new B();
        b.setT(new C());
        System.out.println(b.convertToMultiValueMap());
    }

    /**
     * 将请求参数转化为 application/x-www-form-urlencoded 需要的表单格式
     *
     * @return
     */
    public MultiValueMap<String, String> convertToMultiValueMap() {
        MultiValueMap<String, String> result = new LinkedMultiValueMap<>();
        construct(this, result);
        return result;
    }

    private static void construct(Object parent, MultiValueMap<String, String> result) {
        if (isPrimitive(parent.getClass())) {
            throw new IllegalArgumentException("primitive class can not directly convert to form");
        } else if (Collection.class.isAssignableFrom(parent.getClass())) {
            throw new IllegalArgumentException("collection can not directly convert to form");
        } else {
            construct(parent, null, result);
        }
    }

    private static void construct(Object parent, String prefix, MultiValueMap<String, String> result) {
        String usedPrefix;
        if (Objects.isNull(prefix)) {
            usedPrefix = "";
        } else {
            usedPrefix = prefix + SEPARATOR;
        }
        if (isPrimitive(parent.getClass())) {
            result.put(prefix, Lists.newArrayList(String.valueOf(parent)));
        } else if (Collection.class.isAssignableFrom(parent.getClass())) {
            Collection collection = (Collection) parent;
            Iterator iterator = collection.iterator();
            for (int i = 0; i < collection.size(); i++) {
                Object v = iterator.next();
                construct(v, prefix + "[" + i + "]", result);
            }
        } else if (Map.class.isAssignableFrom(parent.getClass())) {
            Map map = (Map) parent;
            map.forEach((k, v) -> {
                construct(v, usedPrefix + String.valueOf(k), result);
            });
        } else {
            Field[] declaredFields = getAllFields(parent);
            for (Field field : declaredFields) {
                ReflectionUtils.makeAccessible(field);
                Object value = ReflectionUtils.getField(field, parent);
                if (isPrimitive(field.getType())) {
                    result.put(usedPrefix + field.getName(), Lists.newArrayList(String.valueOf(value)));
                } else if (Map.class.isAssignableFrom(field.getType())) {
                    Map map = (Map) value;
                    map.forEach((k, v) -> {
                        construct(v, usedPrefix + field.getName() + SEPARATOR + String.valueOf(k), result);
                    });
                } else if (Collection.class.isAssignableFrom(field.getType())) {
                    Collection collection = (Collection) value;
                    Iterator iterator = collection.iterator();
                    for (int i = 0; i < collection.size(); i++) {
                        Object v = iterator.next();
                        construct(v, usedPrefix + field.getName() + "[" + i + "]", result);
                    }
                } else {
                    construct(value, usedPrefix + field.getName(), result);
                }
            }
        }
    }


    private static Field[] getAllFields(Object object) {
        Class clazz = object.getClass();
        List<Field> fieldList = new ArrayList<>();
        while (clazz != null && clazz != Object.class) {
            List<Field> fields = Lists.newArrayList(clazz.getDeclaredFields());
            fields = fields.stream().filter(
                    field ->
                            (!Modifier.isStatic(field.getModifiers()))
                                    &&
                                    (!Modifier.isFinal(field.getModifiers()))
            ).collect(Collectors.toList());
            fieldList.addAll(fields);
            clazz = clazz.getSuperclass();
        }
        Field[] fields = new Field[fieldList.size()];
        fieldList.toArray(fields);
        return fields;
    }

    private static boolean isPrimitive(Class<?> clazz) {
        return clazz.isPrimitive() || clazz == Boolean.class || clazz == Character.class || clazz == Byte.class || clazz == Short.class || clazz == Integer.class || clazz == Long.class || clazz == Float.class || clazz == Double.class || clazz == BigInteger.class || clazz == BigDecimal.class || clazz == String.class || clazz == java.util.Date.class || clazz == java.sql.Date.class || clazz == Time.class || clazz == Timestamp.class || clazz.isEnum();
    }
}
