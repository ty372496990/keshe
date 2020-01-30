package com.online.edu.school_eduservice.entity.query;

import lombok.Data;

@Data
public class queryTeacher {
    private String name;
    private String level;
    private String begin;//开始时间
    private String end;//结束时间
}
