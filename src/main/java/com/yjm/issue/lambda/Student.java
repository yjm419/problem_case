package com.yjm.issue.lambda;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @author Yjm
 * @date 2023/2/20 14:50
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    private String name;
    private Integer age;

    private volatile static List<Student> students;
    private volatile static Student student;




    public static Student getInstance(){
        if (student == null){
            return new Student("aa%",1);

        }else {
            return student;
        }
    }


    public static List<Student> getInstanceList(){
        if (CollectionUtils.isEmpty(students)){
            List<Student> students = new ArrayList<>();
           students.add(new Student("aa%",1));
           students.add(new Student("bb",2));
           students.add(new Student("cc",3));
           students.add(new Student("dd",4));
           students.add(new Student("ee",5));
           students.add(new Student("ff",6));
            return  students;
        }else {
            return  students;
        }
    }
}
