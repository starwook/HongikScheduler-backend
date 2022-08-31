package com.hongikScheduler.service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetSubjectDto {

    private Long id;

    public int grade;
    public String num;
    public String professor;
    public String subjectName;
    public String date;
}
