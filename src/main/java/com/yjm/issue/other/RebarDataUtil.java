package com.yjm.issue.other;

import com.alibaba.fastjson.JSON;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Yjm
 * @date 2023/2/23 10:27
 */
public class RebarDataUtil {

    private static Map<String,Double> arrivalWeightMap = new HashMap();

    private static  Map<String,Double> consumeDiffMap = new HashMap();
    private static  Map<String,Double> consumeWeightObjMap = new HashMap();
    private static  Map<String,Double> inventoryUsableMap = new HashMap();
    private static  Map<String,Double> testWeightMap = new HashMap();

    private static Map<String,String> dateMap = new HashMap();

    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    public static Map JsonData(String s){
        Map map = JSON.parseObject(s, Map.class);

        Map statStockJsonMap = JSON.parseObject(map.get("statStock").toString(), Map.class);

        // 到货重量
        List<Double> arrivalWeightListList = JSON.parseArray(statStockJsonMap.get("arrivalWeightList").toString(), Double.class);
        // 总验铁
        List<Double> testWeightList = JSON.parseArray(statStockJsonMap.get("testWeightList").toString(), Double.class);
        // 快照日期序列
        List<String> dateList = JSON.parseArray(statStockJsonMap.get("dateList").toString(), String.class);


        // 两次盘点之间的消耗差
        List<String> consumeDiffs = JSON.parseArray(statStockJsonMap.get("consumeDiffList").toString(), String.class);
        // 基于盘点日期的消耗量
        List<String> consumeWeights = JSON.parseArray(statStockJsonMap.get("consumeWeightObjList").toString(), String.class);
        // 库存可用
        List<String> inventoryUsables = JSON.parseArray(statStockJsonMap.get("inventoryUsableList").toString(), String.class);

        disposeJsonList(consumeWeights,consumeDiffMap);
        disposeJsonList(consumeDiffs,consumeWeightObjMap);
        disposeJsonList(inventoryUsables,inventoryUsableMap);
        disposeCount(dateList,testWeightList,arrivalWeightListList);

        statStockJsonMap.put("consumeDiffList", Arrays.toString(consumeDiffMap.keySet().stream().map(key -> "["+key + "," + consumeDiffMap.get(key)+"]").toArray(String[]::new)));
        statStockJsonMap.put("consumeWeightObjList", Arrays.toString(consumeWeightObjMap.keySet().stream().map(key -> "["+key + "," + consumeWeightObjMap.get(key)+"]").toArray(String[]::new)));
        statStockJsonMap.put("inventoryUsableList", Arrays.toString(inventoryUsableMap.keySet().stream().map(key -> "["+key + "," + inventoryUsableMap.get(key)+"]").toArray(String[]::new)));
        statStockJsonMap.put("arrivalWeightList", Arrays.toString(arrivalWeightMap.keySet().stream().map(key -> arrivalWeightMap.get(key)).toArray(Double[]::new)));
        statStockJsonMap.put("testWeightList", Arrays.toString(testWeightMap.keySet().stream().map(key -> testWeightMap.get(key)).toArray(Double[]::new)));
        statStockJsonMap.put("dateList", Arrays.toString(dateMap.keySet().stream().map(key -> dateMap.get(key)).toArray(String[]::new)));
        map.put("statStock",statStockJsonMap);
        System.out.println("map后--->"+map);
        System.out.println("Map的statStock后--->"+map.get("statStock"));
        return map;
    }

    private static void disposeJsonList(List<String> sourceList,Map<String,Double> map){
        List<List> targetList = new ArrayList<>();
        sourceList.stream().forEach(item->{
            String replace = item.replace("[", "").replace("]", "").replace("\"","");
            targetList.add(Arrays.asList(replace.substring(0,replace.indexOf(",")),replace.substring(replace.indexOf(",")+1,replace.length())));
        });
        List timeList = targetList.stream().map(list -> list.get(0)).collect(Collectors.toList());
        List countList = targetList.stream().map(list -> list.get(1)).collect(Collectors.toList());
        disposeList(timeList,countList,map);


    }

    private static void  disposeList(List timeList,List countList,Map<String,Double> map)  {
        Calendar calendar = Calendar.getInstance();
        for (int i = 0; i < timeList.size(); i++) {
            Date date = null;
            try {
                date = dateFormat.parse(timeList.get(i).toString());
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            calendar.setTime(date);
            String date1 = calendar.get(Calendar.YEAR) +"-"+ (calendar.get(Calendar.MONTH)+1);
            map.put(date1,0.0);
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
            map.put(date1,map.get(date1)+Double.parseDouble((String) countList.get(i)));
        }

    }

    private static void disposeCount(List<String> dateList,List<Double> testWeightList,List<Double> arrivalWeightListList){
        Calendar calendar = Calendar.getInstance();
        for (int i = 0; i < dateList.size(); i++) {
            Date date = null;
            try {
                date = dateFormat.parse(dateList.get(i));
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            calendar.setTime(date);
            String date1 = calendar.get(Calendar.YEAR) +"-"+ (calendar.get(Calendar.MONTH)+1);
            arrivalWeightMap.put(date1,0.0);
            testWeightMap.put(date1,0.0);
            dateMap.put(date1,date1);
        }

        for (int i = 0; i < dateList.size(); i++) {
            Date date = null;
            try {
                date = dateFormat.parse(dateList.get(i));
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            calendar.setTime(date);
            String data1 = calendar.get(Calendar.YEAR) +"-"+ (calendar.get(Calendar.MONTH)+1);

            arrivalWeightMap.put(data1,arrivalWeightMap.get(data1)+arrivalWeightListList.get(i));
            testWeightMap.put(data1,testWeightMap.get(data1)+testWeightList.get(i));
        }
    }
}
