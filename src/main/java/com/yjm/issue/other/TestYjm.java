package com.yjm.issue.other;

import com.alibaba.fastjson.JSON;


import java.security.Key;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
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


        Map map = JSON.parseObject(s, Map.class);

        Map yMap = JSON.parseObject(map.get("statStock").toString(), Map.class);
        System.out.println("yMap--->"+yMap);

        yMap.forEach((key,value)-> {
            Map rebarJsonMap = JSON.parseObject(yMap.get(key).toString(), Map.class);

            List<String> consumeDiffs = JSON.parseArray(rebarJsonMap.get("consumeDiffList").toString(), String.class);
            List<String> consumeWeights = JSON.parseArray(rebarJsonMap.get("consumeWeightObjList").toString(), String.class);
            List<String> inventoryUsables = JSON.parseArray(rebarJsonMap.get("inventoryUsableList").toString(), String.class);

            //---------------------------------------------------------
            List<List> consumeDiffList = new ArrayList<>();
            consumeDiffs.stream().forEach(item->{
                String replace = item.replace("[", "").replace("]", "").replace("\"","");
                consumeDiffList.add(Arrays.asList(replace.substring(0,replace.indexOf(",")),replace.substring(replace.indexOf(",")+1,replace.length())));
            });

            List<List> consumeWeightList = new ArrayList<>();
            consumeWeights.stream().forEach(item->{
                String replace = item.replace("[", "").replace("]", "").replace("\"","");
                consumeWeightList.add(Arrays.asList(replace.substring(0,replace.indexOf(",")),replace.substring(replace.indexOf(",")+1,replace.length())));
            });

            List<List> inventoryUsableList = new ArrayList<>();
            inventoryUsables.stream().forEach(item->{
                String replace = item.replace("[", "").replace("]", "").replace("\"","");
                inventoryUsableList.add(Arrays.asList(replace.substring(0,replace.indexOf(",")),replace.substring(replace.indexOf(",")+1,replace.length())));
            });

            //---------------------------------------------------------
            Calendar calendar = Calendar.getInstance();
            List timeList = consumeDiffList.stream().map(list -> list.get(0)).collect(Collectors.toList());
            List amountList = consumeDiffList.stream().map(list -> list.get(1)).collect(Collectors.toList());

            for (int i = 0; i < timeList.size(); i++) {
                Date date = null;
                try {
                    date = dateFormat.parse(timeList.get(i).toString());
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                calendar.setTime(date);
                String date1 = calendar.get(Calendar.YEAR) +"-"+ (calendar.get(Calendar.MONTH)+1);
                consumeDiffMap.put(date1,0.0);
            }

            for (int i = 0; i < timeList.size(); i++) {
                Date date = null;
                try {
                    date = dateFormat.parse(timeList.get(i).toString());
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                calendar.setTime(date);
                String date1 = calendar.get(Calendar.YEAR) +"-"+ (calendar.get(Calendar.MONTH)+1);
                consumeDiffMap.put(date1,consumeDiffMap.get(date1)+Double.parseDouble((String) amountList.get(i)));
            }

            rebarJsonMap.put("consumeDiffList", Arrays.toString(consumeDiffMap.keySet().stream().map(key1 -> "["+key1 + "," + consumeDiffMap.get(key1)+"]").toArray(String[]::new)));
            yMap.put(key,rebarJsonMap.toString());
        });


//        Map listMapY40 = JSON.parseObject(yMap.get("Y40").toString(), Map.class);
//        Map listMapY20 = JSON.parseObject(yMap.get("Y20").toString(), Map.class);
//        System.out.println("listMapY40--->"+listMapY40);
//        System.out.println("listMapY20--->"+listMapY20);


//
//
//        List<String> consumeDiffs = JSON.parseArray(statStockMap.get("consumeDiffList").toString(), String.class);
//        System.out.println(consumeDiffs);

//        Map statStockMap = JSON.parseObject(map.get("statStock").toString(), Map.class);


        // 到货重量
//        List<Double> arrivalWeightListList = JSON.parseArray(statStockMap.get("arrivalWeightList").toString(), Double.class);
//        // 总验铁
//        List<Double> testWeightList = JSON.parseArray(statStockMap.get("testWeightList").toString(), Double.class);
//        // 快照日期序列
//        List<String> dateList = JSON.parseArray(statStockMap.get("dateList").toString(), String.class);
//
//
//        // 两次盘点之间的消耗差
//        List<String> consumeDiffs = JSON.parseArray(statStockMap.get("consumeDiffList").toString(), String.class);
//        // 基于盘点日期的消耗量
//        List<String> consumeWeights = JSON.parseArray(statStockMap.get("consumeWeightObjList").toString(), String.class);
//        // 库存可用
//        List<String> inventoryUsables = JSON.parseArray(statStockMap.get("inventoryUsableList").toString(), String.class);
//
//
//
//        Calendar calendar = Calendar.getInstance();

//        List<List> consumeDiffList = new ArrayList<>();
//        consumeDiffs.stream().forEach(item->{
//            String replace = item.replace("[", "").replace("]", "").replace("\"","");
//            consumeDiffList.add(Arrays.asList(replace.substring(0,replace.indexOf(",")),replace.substring(replace.indexOf(",")+1,replace.length())));
//        });
//
//        List<List> consumeWeightList = new ArrayList<>();
//        consumeWeights.stream().forEach(item->{
//            String replace = item.replace("[", "").replace("]", "").replace("\"","");
//            consumeWeightList.add(Arrays.asList(replace.substring(0,replace.indexOf(",")),replace.substring(replace.indexOf(",")+1,replace.length())));
//        });
//
//        List<List> inventoryUsableList = new ArrayList<>();
//        inventoryUsables.stream().forEach(item->{
//            String replace = item.replace("[", "").replace("]", "").replace("\"","");
//            inventoryUsableList.add(Arrays.asList(replace.substring(0,replace.indexOf(",")),replace.substring(replace.indexOf(",")+1,replace.length())));
//        });
//
//        //---------------------------------------------------------------------------------------
//
//
//        List timeList = consumeDiffList.stream().map(list -> list.get(0)).collect(Collectors.toList());
//        List amountList = consumeDiffList.stream().map(list -> list.get(1)).collect(Collectors.toList());
//
//        for (int i = 0; i < timeList.size(); i++) {
//            Date date = dateFormat.parse(timeList.get(i).toString());
//            calendar.setTime(date);
//            String date1 = calendar.get(Calendar.YEAR) +"-"+ (calendar.get(Calendar.MONTH)+1);
//            consumeDiffMap.put(date1,0.0);
//        }
//
//        for (int i = 0; i < timeList.size(); i++) {
//            Date date = dateFormat.parse(timeList.get(i).toString());
//            calendar.setTime(date);
//            String date1 = calendar.get(Calendar.YEAR) +"-"+ (calendar.get(Calendar.MONTH)+1);
//            consumeDiffMap.put(date1,consumeDiffMap.get(date1)+Double.parseDouble((String) amountList.get(i)));
//        }
//
//
//        List timeList2 = consumeWeightList.stream().map(list -> list.get(0)).collect(Collectors.toList());
//        List amountList2 = consumeWeightList.stream().map(list -> list.get(1)).collect(Collectors.toList());
//
//        for (int i = 0; i < timeList2.size(); i++) {
//            Date date = dateFormat.parse(timeList2.get(i).toString());
//            calendar.setTime(date);
//            String date1 = calendar.get(Calendar.YEAR) +"-"+ (calendar.get(Calendar.MONTH)+1);
//            consumeWeightObjMap.put(date1,0.0);
//        }
//
//        for (int i = 0; i < timeList2.size(); i++) {
//            Date date = dateFormat.parse(timeList2.get(i).toString());
//            calendar.setTime(date);
//            String date1 = calendar.get(Calendar.YEAR) +"-"+ (calendar.get(Calendar.MONTH)+1);
//            consumeWeightObjMap.put(date1,consumeWeightObjMap.get(date1)+Double.parseDouble((String) amountList2.get(i)));
//        }
//
//        List timeList3 = inventoryUsableList.stream().map(list -> list.get(0)).collect(Collectors.toList());
//        List amountList3 = inventoryUsableList.stream().map(list -> list.get(1)).collect(Collectors.toList());
//
//        for (int i = 0; i < timeList3.size(); i++) {
//            Date date = dateFormat.parse(timeList3.get(i).toString());
//            calendar.setTime(date);
//            String date1 = calendar.get(Calendar.YEAR) +"-"+ (calendar.get(Calendar.MONTH)+1);
//            inventoryUsableMap.put(date1,0.0);
//        }
//
//        for (int i = 0; i < timeList3.size(); i++) {
//            Date date = dateFormat.parse(timeList2.get(i).toString());
//            calendar.setTime(date);
//            String date1 = calendar.get(Calendar.YEAR) +"-"+ (calendar.get(Calendar.MONTH)+1);
//            inventoryUsableMap.put(date1,inventoryUsableMap.get(date1)+Double.parseDouble((String) amountList3.get(i)));
//        }
//
//
//
//
//
//        statStockMap.put("consumeDiffList", Arrays.toString(consumeDiffMap.keySet().stream().map(key -> "["+key + "," + consumeDiffMap.get(key)+"]").toArray(String[]::new)));
//        statStockMap.put("consumeWeightObjList", Arrays.toString(consumeWeightObjMap.keySet().stream().map(key -> "["+key + "," + consumeWeightObjMap.get(key)+"]").toArray(String[]::new)));
//        statStockMap.put("inventoryUsableList", Arrays.toString(inventoryUsableMap.keySet().stream().map(key -> "["+key + "," + inventoryUsableMap.get(key)+"]").toArray(String[]::new)));
//
//
//
//
//
//        for (int i = 0; i < dateList.size(); i++) {
//            Date date = dateFormat.parse(dateList.get(i));
//            calendar.setTime(date);
//            String date1 = calendar.get(Calendar.YEAR) +"-"+ (calendar.get(Calendar.MONTH)+1);
//            arrivalWeightMap.put(date1,0.0);
//            testWeightMap.put(date1,0.0);
//            dateMap.put(date1,date1);
//        }
//
//        for (int i = 0; i < dateList.size(); i++) {
//            Date date = dateFormat.parse(dateList.get(i));
//            calendar.setTime(date);
//            String data1 = calendar.get(Calendar.YEAR) +"-"+ (calendar.get(Calendar.MONTH)+1);
//
//            arrivalWeightMap.put(data1,arrivalWeightMap.get(data1)+arrivalWeightListList.get(i));
//            testWeightMap.put(data1,testWeightMap.get(data1)+testWeightList.get(i));
//        }
//
//        statStockMap.put("arrivalWeightList", Arrays.toString(arrivalWeightMap.keySet().stream().map(key -> arrivalWeightMap.get(key)).toArray(Double[]::new)));
//        statStockMap.put("testWeightList", Arrays.toString(testWeightMap.keySet().stream().map(key -> testWeightMap.get(key)).toArray(Double[]::new)));
//        statStockMap.put("dateList", Arrays.toString(dateMap.keySet().stream().map(key -> dateMap.get(key)).toArray(String[]::new)));
//
        map.put("statStock",yMap);

        System.out.println("Map后--->"+map.get("statStock"));


    }
}
