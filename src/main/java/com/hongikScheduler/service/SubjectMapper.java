package com.hongikScheduler.service;


import com.hongikScheduler.domain.Subject;
import com.hongikScheduler.service.dto.response.GetSubjectDto;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public interface SubjectMapper {
    default List<GetSubjectDto> toSubject(List<Subject> subjectList){
        List<GetSubjectDto> getSubjectDtoList = new ArrayList<>();
        for(Subject subject :subjectList){
            GetSubjectDto getSubjectDto = new GetSubjectDto();
            getSubjectDto.setId(subject.getId());
            getSubjectDto.setSubjectName(subject.getSubjectName());
            getSubjectDto.setDate(subject.getDate());
            getSubjectDto.setGrade(subject.getGrade());
            getSubjectDto.setNum(subject.getNum());
            getSubjectDto.setProfessor(subject.getProfessor());
            getSubjectDtoList.add(getSubjectDto);
        }
        return getSubjectDtoList;
    }

}
