package com.hongikScheduler.service;


import com.hongikScheduler.domain.Subject;
import com.hongikScheduler.domain.SubjectRepository;
import com.hongikScheduler.service.dto.response.GetSubjectDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QueryProcessor {
    private final SubjectRepository subjectRepository;
    private final SubjectMapper subjectMapper;

    public List<GetSubjectDto> getAllSubject(){
        List<Subject> subjectList = subjectRepository
                .findAll()
                .stream()
                .collect(Collectors.toList());
        if(subjectList.isEmpty()){
            throw new IllegalArgumentException("아무것도 없음");
        }
        return subjectMapper.toSubject(subjectList);
    }
}
