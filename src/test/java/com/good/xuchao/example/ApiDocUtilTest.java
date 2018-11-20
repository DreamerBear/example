package com.good.xuchao.example;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

/**
 * <p> </p>
 *
 * @author: 绪超（xuchao@maihaoche.com）
 * @date: 2018/6/25 下午3:25
 * @since V1.1.0-SNAPSHOT
 */
public class ApiDocUtilTest {
    @Test
    public void test() throws IOException {
        String a = "com.subaru.common.entity.APIResult<java.util.List<com.mhc.mclaren.api.model.dto.WmsCarRichInfoDTO>>";
        String s1 = a.replaceAll("[a-zA-Z0-9]+\\.", "");
//        System.out.println(s1);


        a = "private Long recordId;\n" +
                "    private String deliveryWarehouseName; // 出库仓库名称\n" +
                "    private Long deliveryDate; // 出库时间\n" +
                "    private String entryWarehouseName; // 入库仓库名称\n" +
                "    private Long entryDate; // 入库时间";
        StringBuilder sb = new StringBuilder();
        StringReader sr = new StringReader(a.replace("//","\n\r"));
        BufferedReader br = new BufferedReader(sr);
        String s = null;
        while ((s = br.readLine()) != null){
            if(!s.contains("/**") && !s.contains("wms_car") && !s.contains("*/")){
                s=s.replaceAll("\\*","");
                s=s.replaceAll("private","");
                s=s.trim();
                s=s.replace(" ","\t");
                if(StringUtils.isNotBlank(s)){
                    if(s.endsWith(";")){
                        s=s.replaceAll(";","\n\r");
                        sb.append(s);
                    }else {
                        sb.append(s);
                        sb.append("\t");
                    }
                }
            }

        }
        System.out.println(sb.toString());

    }
}
