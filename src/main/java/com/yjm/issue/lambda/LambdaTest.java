package com.yjm.issue.lambda;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Yjm
 * @date 2023/2/20 14:52
 */
public class LambdaTest {

    public static void main(String[] args) {
        List<Student> studentList = Student.getInstanceList();
        System.out.println(lambdaPeplacePart(studentList));


    }




    /**
     * 取前index条数据
     * @param studentList 输入的集合
     * @param index 前几条
     */
    public static void lambdaGetFewDate( List<Student> studentList,Integer index){
            //limit 获取集合中前index条数据
            studentList.stream().limit(index).forEach(System.out::print);

    }

    /**
     * 取第index条以后数据
     * @param studentList 输入的集合
     * @param index 后几条所以
     */
    public static void lambdaGetAfterDate( List<Student> studentList,Integer index){
            //limit 获取集合中前index条数据
            studentList.stream().skip(index).forEach(System.out::print);
    }

    /**
     * 替换集合中某个属性的值
     * @param studentList
     * @return
     */
    public static List<Student> lambdaPeplacePart(List<Student> studentList){

        List<Student> students = studentList.stream().map(item -> {
            item.setName(item.getName().replace("%", ""));
            return item;
        }).collect(Collectors.toList());
        return students;

    }





}
