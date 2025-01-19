package com.matchacloud.summerstarter.student.domain.dto;

import lombok.Data;

@Data
public class StudentDTO {
    private String studentId;
    private String name;
    private int age;
    private String major;
    private double gpa;
}
