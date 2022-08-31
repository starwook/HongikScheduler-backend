package com.hongikScheduler.service;


import com.hongikScheduler.domain.Subject;
import com.hongikScheduler.domain.SubjectRepository;
import com.hongikScheduler.service.dto.response.GetSubjectDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QueryProcessor {
    private final SubjectRepository subjectRepository;
    private final SubjectMapper subjectMapper;

    public List<GetSubjectDto> getAllSubjectDto(){
        List<Subject> subjectList = subjectRepository
                .findAll()
                .stream()
                .collect(Collectors.toList());
        if(subjectList.isEmpty()){
            throw new IllegalArgumentException("아무것도 없음");
        }
        return subjectMapper.toSubject(subjectList);
    }
    public List<Subject> getAllSubject(){
        Subject subject = new Subject("3",  "김일도", "자료구조");
        Subject subject1 = new Subject("2",  "김명", "인공지능");
        Subject subject2 = new Subject("1", "김도", "자료구조");
        Subject subject3 = new Subject("4", "김도", "운영체제");
        List<Subject> subjectList = new ArrayList<>();
        subjectList.add(subject);
        subjectList.add(subject1);
        subjectList.add(subject2);
        subjectList.add(subject3);
        return subjectList;
    }
}
