package com.hongikScheduler.domain;


import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Entity
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public int grade;
    public String num; //학수번호
    public String special; //비고
    public String professor;
    public String subjectName;
    public String date;





    public Subject(String grade, String num, String special, String professor, String subjectName, String date) {
        this.grade = Integer.parseInt(grade);
        this.num = num;
        this.special = special;
        this.professor = professor;
        this.subjectName = subjectName;
        this.date = date;
    }

    public Subject() {

    }
}
