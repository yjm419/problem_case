package com.yjm.issue.other;

import com.alibaba.fastjson.JSON;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * @author Yjm
 * @date 2023/2/22 12:54
 */
public class TestYjm {

    private static Map<String,Double> arrivalWeightMap = new HashMap();

    private static  Map<String,Double> consumeDiffMap = new HashMap();
    private static  Map<String,Double> consumeWeightObjMap = new HashMap();
    private static  Map<String,Double> inventoryUsableMap = new HashMap();
    private static  Map<String,Double> testWeightMap = new HashMap();
    private static Map<String,String> dateMap = new HashMap();



    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private static  YjmString yjm = new YjmString();

    private static  final  String s = yjm.getYjmString();
    public static void main(String[] args) throws ParseException {
        String s1 = RebarDataUtil.JsonData(s);
        System.out.println(s1);

//        Map map = JSON.parseObject(s, Map.class);
//        System.out.println("原map-->"+map);
//
//        List<Map> newMap = new ArrayList<>();
//        List<Map> predictStats = JSON.parseArray(map.get("predictStat").toString(), Map.class);
//
//        predictStats = predictStats.stream()
//                .sorted(Comparator.comparing(item -> Double.parseDouble(item.get("inventoryUsable").toString()),Comparator.reverseOrder()))
//                .collect(Collectors.toList());
//
//        if (predictStats.size()>4){
//            Map otherMap = new HashMap<>();
//            otherMap.put("category","其他");
//            otherMap.put("inventoryUsable",0.0);
//            predictStats.stream().limit(4).forEach(item->{
//                newMap.add(item);
//            });
//            predictStats.stream().skip(4).forEach(item->{
//                double coun = Double.parseDouble(otherMap.get("inventoryUsable").toString()) + Double.parseDouble(item.get("inventoryUsable").toString());
//                otherMap.put("inventoryUsable",coun);
//
//            });
//            newMap.add(otherMap);
//
//        }
//        map.put("predictStatCount",newMap);
    }
}
