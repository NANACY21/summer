package com.matchacloud.summerstarter.student.domain.vo;

import lombok.Data;

@Data
public class StudentVO {
    private String studentId;
    private String name;
    private int age;
    private String major;
    private double gpa;
}
