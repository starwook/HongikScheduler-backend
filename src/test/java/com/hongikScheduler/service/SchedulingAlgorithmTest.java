package com.hongikScheduler.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class SchedulingAlgorithmTest {

    @Autowired
    private SchedulingAlgorithm schedulingService;


    @Test
    @Transactional
    public void testing(){
        List<String> selectedSubjects = new ArrayList<>();
        selectedSubjects.add("운영체제");
        selectedSubjects.add("기초데이터베이스");
        selectedSubjects.add("오토마타");
        schedulingService.findAllSchedules(selectedSubjects);

    }

}