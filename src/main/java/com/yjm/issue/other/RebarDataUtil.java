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
    public static String JsonData(String s){
        Map map = JSON.parseObject(s, Map.class);

        List<Map> newMap = new ArrayList<>();
        List<Map> categoryArrivedStats = JSON.parseArray(map.get("categoryArrivedStat").toString(), Map.class);
        categoryArrivedStats = categoryArrivedStats.stream()
                .sorted(Comparator.comparing(item -> Double.parseDouble(item.get("totalWeight").toString()),Comparator.reverseOrder()))
                .collect(Collectors.toList());
        if (categoryArrivedStats.size()>4){
            Map otherMap = new HashMap<>();
            otherMap.put("category","其他");
            otherMap.put("totalWeight",0.0);
            categoryArrivedStats.stream().limit(4).forEach(item->{
                newMap.add(item);
            });
            categoryArrivedStats.stream().skip(4).forEach(item->{
                double coun = Double.parseDouble(otherMap.get("totalWeight").toString()) + Double.parseDouble(item.get("totalWeight").toString());
                otherMap.put("totalWeight",coun);

            });
            newMap.add(otherMap);

        }
        map.put("categoryArrivedStat",newMap);



        List<Map> newMap1 = new ArrayList<>();
        List<Map> predictStats = JSON.parseArray(map.get("predictStat").toString(), Map.class);

        predictStats = predictStats.stream()
                .sorted(Comparator.comparing(item -> Double.parseDouble(item.get("inventoryUsable").toString()),Comparator.reverseOrder()))
                .collect(Collectors.toList());

        if (predictStats.size()>4){
            Map otherMap = new HashMap<>();
            otherMap.put("category","其他");
            otherMap.put("inventoryUsable",0.0);
            predictStats.stream().limit(4).forEach(item->{
                newMap.add(item);
            });
            predictStats.stream().skip(4).forEach(item->{
                double coun = Double.parseDouble(otherMap.get("inventoryUsable").toString()) + Double.parseDouble(item.get("inventoryUsable").toString());
                otherMap.put("inventoryUsable",coun);

            });
            newMap1.add(otherMap);

        }
        map.put("predictStatCount",newMap1);


        Map yMap = JSON.parseObject(map.get("statStock").toString(), Map.class);
        Calendar calendar = Calendar.getInstance();
        yMap.forEach((key,value)-> {
            Map rebarJsonMap = JSON.parseObject(yMap.get(key).toString(), Map.class);
            List<Double> arrivalWeightListList = JSON.parseArray(rebarJsonMap.get("arrivalWeightList").toString(), Double.class);
            List<Double> testWeightList = JSON.parseArray(rebarJsonMap.get("testWeightList").toString(), Double.class);
            List<String> dateList = JSON.parseArray(rebarJsonMap.get("dateList").toString(), String.class);
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

            rebarJsonMap.forEach((k,v)->{

                if (k.equals("consumeDiffList")||k.equals("consumeWeightObjList")||k.equals("inventoryUsableList")){

                    List<String> targets = JSON.parseArray(rebarJsonMap.get(k).toString(), String.class);
                    List<List> targetsList = new ArrayList<>();
                    targets.stream().forEach(item->{
                        String replace = item.replace("[", "").replace("]", "").replace("\"","");
                        targetsList.add(Arrays.asList(replace.substring(0,replace.indexOf(",")),replace.substring(replace.indexOf(",")+1,replace.length())));
                    });
                    List timeList = targetsList.stream().map(list -> list.get(0)).collect(Collectors.toList());
                    List amountList = targetsList.stream().map(list -> list.get(1)).collect(Collectors.toList());


                    for (int i = 0; i < timeList.size(); i++) {
                        Date date = null;
                        try {
                            date = dateFormat.parse(timeList.get(i).toString());
                        } catch (ParseException e) {
                            throw new RuntimeException(e);
                        }
                        calendar.setTime(date);
                        String date1 = calendar.get(Calendar.YEAR) +"-"+ (calendar.get(Calendar.MONTH)+1);
                        if (k.equals("consumeDiffList")){
                            consumeDiffMap.put(date1,0.0);
                        }
                        if (k.equals("consumeWeightObjList")){
                            consumeWeightObjMap.put(date1,0.0);
                        }
                        if (k.equals("inventoryUsableList")){
                            inventoryUsableMap.put(date1,0.0);
                        }

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
                        if (k.equals("consumeDiffList")){
                            consumeDiffMap.put(date1,consumeDiffMap.get(date1)+Double.parseDouble((String) amountList.get(i)));
                        }
                        if (k.equals("consumeWeightObjList")){
                            consumeWeightObjMap.put(date1,consumeWeightObjMap.get(date1)+Double.parseDouble((String) amountList.get(i)));
                        }
                        if (k.equals("inventoryUsableList")){
                            inventoryUsableMap.put(date1,inventoryUsableMap.get(date1)+Double.parseDouble((String) amountList.get(i)));
                        }
                    }
                }
            });
            rebarJsonMap.put("consumeDiffList", consumeDiffMap.keySet().stream().map(key1 -> "["+key1 + "," + consumeDiffMap.get(key1)+"]").toArray(String[]::new));
            rebarJsonMap.put("consumeWeightObjList", consumeWeightObjMap.keySet().stream().map(key1 -> "["+key1 + "," + consumeWeightObjMap.get(key1)+"]").toArray(String[]::new));
            rebarJsonMap.put("inventoryUsableList", inventoryUsableMap.keySet().stream().map(key1 -> "["+key1 + "," + inventoryUsableMap.get(key1)+"]").toArray(String[]::new));
            rebarJsonMap.put("arrivalWeightList", arrivalWeightMap.keySet().stream().map(key1 -> arrivalWeightMap.get(key1)).toArray(Double[]::new));
            rebarJsonMap.put("testWeightList", testWeightMap.keySet().stream().map(key1 -> testWeightMap.get(key1)).toArray(Double[]::new));
            rebarJsonMap.put("dateList", dateMap.keySet().stream().map(key1 -> dateMap.get(key1)).toArray(String[]::new));
            yMap.put(key, rebarJsonMap);
            System.out.println("rebarJsonMap--->"+JSON.parse(JSON.toJSONString(rebarJsonMap)));

        });


        map.put("statStock",yMap);

        return JSON.toJSONString(map);

    }
}
