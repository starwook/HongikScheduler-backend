package com.hongikScheduler.service;


import com.hongikScheduler.domain.Subject;
import com.hongikScheduler.domain.SubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class SchedulingService {
    private final SubjectRepository subjectRepository;
    private final String [][] schedulers = new String[15][6];
    private final List<Subject> finalSubjects = new ArrayList<>();//선택된 과목의 전체
    private final List<String> selectedSubjectName = new ArrayList<>();
    private final List<Pair<Integer,String>> subjectNumAndNamePair = new ArrayList<>();
    private int  totalCount = 0;
    @Transactional
    public void findAllSchedules(List<String> choosedSubjects){

        init();
        List<Subject> allSubjects = subjectRepository.findAll(); //전체과목
        for(Subject findSubject: allSubjects){ //전체에서 검사
            for(String findSubjectName : choosedSubjects){
                if(findSubject.getSubjectName().contains(findSubjectName)){
                    finalSubjects.add(findSubject);
                }
            }
        }
        int cnt =0;
        for(String string: choosedSubjects){
            subjectNumAndNamePair.add(Pair.of(cnt+1,choosedSubjects.get(cnt)));
            cnt++;
        }
//        for(int i=0;i<=finalSubjects.size()-choosedSubjects.size();i++){
//        }
        checking(choosedSubjects.size(),0, finalSubjects,choosedSubjects.size(),choosedSubjects.get(0));
        System.out.println("나온 스케쥴은 총:"+totalCount);
//        for(int i=0;i<finalSubjects.size();i++){
//            Subject firstSubject = finalSubjects.get(i);
//            List<String> dateTime = splitTime(firstSubject); //,를 구별해서 분리
////            for(String string : dateTime){
////                System.out.println(string);
////            }
//            String description = firstSubject.getSubjectName()+", "+firstSubject.getProfessor();
//
//
//            // BackTracking
//
//        }
    }
    @Transactional
    public void checking(int last,int i,List<Subject> subjects,int total,String firstMustSubject){
        System.out.println("i는 현재:"+i);
        System.out.println("현재 선택된 과목의 수 = "+selectedSubjectName+"최종 목표 과목 수"+ total);
        if(selectedSubjectName.size() ==total){
           showSchedules();
           totalCount++;
           System.out.println("지금까지 나온 스케쥴은 총:"+totalCount);
           return;
        }
        for(int j=i;j<finalSubjects.size();j++){
            List<Pair<Integer,Integer>> pairList = new ArrayList<>();
            if (selectedSubjectName.contains(subjects.get(j).getSubjectName())) {
                continue;
            }
            Subject firstSubject = subjects.get(j);
            List<String> dateTime = splitTime(firstSubject);
            String description = firstSubject.getSubjectName().substring(0,2) + "," + firstSubject.getProfessor().substring(0,1);
            pairList = splitTimeAndTry(dateTime);
            for(Pair<Integer,Integer> pair :pairList) {
                if (!checkPossible(pair)) {
                    return;
                }
            }
            for(Pair<Integer,Integer> pair :pairList){
                schedulers[pair.getSecond()][pair.getFirst()] = description;
            }
            selectedSubjectName.add(firstSubject.getSubjectName());
            if(!selectedSubjectName.contains(firstMustSubject)){
                return;
            }
            checking(last, j + 1, subjects,total,firstMustSubject);
            for(Pair<Integer,Integer> pair: pairList){
                System.out.println("X로 바꿔주기");
                schedulers[pair.getSecond()][pair.getFirst()] = "X";
            }
            selectedSubjectName.remove(firstSubject.getSubjectName());
        }

    }
    @Transactional
    public boolean checkPossible(Pair<Integer,Integer> pair){
        if(schedulers[pair.getSecond()][pair.getFirst()].equals("X")){
            System.out.println(pair.getSecond()+" , "+ pair.getFirst());
            return true;
        }
        else{
            System.out.println(schedulers[pair.getSecond()][pair.getFirst()]+"현재 선택한 곳의 위치");
            System.out.println("시간이 겹침!");
            return false;
        }
    }
    @Transactional
    public void init(){

        for(int r=1;r<15;r++){
            for(int c=0;c<5;c++){
                schedulers[r][c] ="X";
            }
        }
        for(int i=0;i<15;i++){
            schedulers[i][5] = Integer.toString(i + 8) + "시";
            if(i==0){
                for(int j=0;j<6;j++){
                    if(j==0)schedulers[i][j] ="월";
                    if(j==1)schedulers[i][j] ="화";
                    if(j==2)schedulers[i][j] ="수";
                    if(j==3)schedulers[i][j] ="목";
                    if(j==4)schedulers[i][j] ="금";
                    if(j==5)schedulers[i][j] ="시간";
                }
            }
        }
    }
    @Transactional
    public List<String> splitTime( Subject subject){ //날짜 나누기
        List<String> date = new ArrayList<>();
        String tmpDate = subject.getDate();
        int startNum=0;
        for(int i=0;i<tmpDate.length();i++){
            if(tmpDate.charAt(i) == ','){
                date.add(tmpDate.substring(startNum,i));
                startNum = i+1;
            }
        }
        date.add(tmpDate.substring(startNum));
        return date;
    }
    @Transactional
    public void reTime(String [][] scheduler){

    }
    @Transactional
    public List<Pair<Integer,Integer> > splitTimeAndTry( List<String> dates){
        int col =0;
        List<Pair<Integer,Integer>> pairList= new ArrayList<Pair<Integer, Integer>>();
        int row[] = new int[3];
        int rowNum=0;
        int tmpRowNum =rowNum;
        for(String date :dates){
            for(int i=0;i<date.length();i++){
                if(i==0){  //날짜
                    char tmp = date.charAt(i);
                    if(tmp =='월'){
                        col =0;
                    }
                    else if(tmp =='화'){
                        col =1;
                    }
                    else if(tmp =='수'){
                        col =2;
                    }
                    else if(tmp =='목'){
                        col =3;
                    }
                    else {//금일경우
                        col =4;
                    }
                }
                else{
                    String onlyTime = date.substring(1);
                    for(int j=0;j<onlyTime.length();j++){
                        if(onlyTime.charAt(j)-'0' == 1){ //시작이 1이라면
                            if(j==onlyTime.length()-1){ //마지막 글자라면
                                row[rowNum] = onlyTime.charAt(j) - '0';
                                rowNum++;
                            }
                            else{//1시작 다음에 숫자가 온다면
                                int tmpJ = j+1;
                                if(onlyTime.charAt(tmpJ)-'0' == 0){ //10
                                    row[rowNum] = 10;
                                    rowNum++;
                                }
                                else if(onlyTime.charAt(tmpJ)-'0' ==1){ //11
                                    row[rowNum] =11;
                                    rowNum++;
                                }
                                else if(onlyTime.charAt(tmpJ)-'0'==2){ //12이거나 1,2일경우 둘로 나누어짐 이건 구분 불가이기떄문에
                                    if(rowNum==0){ //처음일땐 그냥 1,2로 퉁
                                        row[rowNum] = 1;
                                        row[rowNum+1] = 2;
                                        rowNum +=2;
                                    }
                                    else{ //아니라면 12
                                        row[rowNum] =12;
                                        rowNum++;
                                    }
                                }
                                else{
                                    row[rowNum] = 10+onlyTime.charAt(tmpJ)-'0';
                                    rowNum++;
                                }
                                j++;
                            }
                        }
                        else{
                            row[rowNum] =onlyTime.charAt(j)-'0';
                            rowNum ++;
                        }
                    }
                    tmpRowNum = rowNum;
                    rowNum=0;
                }
            }
            for(int i=0;i<tmpRowNum;i++){
                pairList.add(Pair.of(col,row[i]));
            }
        }
        return pairList;

    }

    @Transactional
    public void showSchedules() throws FormatFlagsConversionMismatchException {
        for(int r=0;r<15;r++){  //forTest
            for(int c =0;c<6;c++){
                System.out.printf("%20s", schedulers[r][c]);
//                if(r != 0 &&c<5){
//                    scheduler[r][c] ="X";
//                }
            }
            System.out.println();
        }
    }
}
