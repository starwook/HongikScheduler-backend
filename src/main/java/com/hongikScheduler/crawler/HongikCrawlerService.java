package com.hongikScheduler.crawler;


import com.hongikScheduler.domain.Subject;
import com.hongikScheduler.domain.SubjectRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
@Slf4j
public class HongikCrawlerService {


    String MainWindow = new String();
    private final SubjectRepository subjectRepository;
    private static final String urlHongikSugang = "https://sugang.hongik.ac.kr/";
    private static final String urlHongik = "https://www.hongik.ac.kr/login.do";

    @PostConstruct
    public void getData() throws InterruptedException{

        Selenium selenium = new Selenium();
        WebDriver driver = selenium.getDriver();
        MainWindow = driver.getWindowHandle();
        loginSugang(driver);

        ///Jsoup
        for(int i=1;i<=4;i++){
            findComputerData(driver,i);
            String html = driver.getPageSource();
            Document doc = Jsoup.parse(html);
            Elements elements = doc.select("body > table > tbody > tr:nth-child(2) > td > table > tbody > tr > td:nth-child(3) > div:nth-child(2) > form > table > tbody> tr > td > table > tbody");
//            body > table > tbody > tr:nth-child(2) > td > table > tbody > tr > td:nth-child(3) > div:nth-child(2) > form > table > tbody > tr > td > table > tbody > tr:nth-child(4) > td:nth-child(2)
            int count  = elements.select("tr").size()/2;
            if((count-3)%2 ==1){
                System.out.println(count-3+":과목 개수");
            }
            else{
                System.out.println(count-4+":과목 개수");
            }
            if(count <=8){
                log.warn("과목없음");
                continue;
            }
            int firstNum =0;
            for(int j = 0;j<= (count-2)*2;j++){
                if(j%2 !=firstNum ){
                    continue;
                }
                if(j<4){
                    continue;
                }
                try
                {
//                    System.out.println(j +"= count의 현재 수");
                    //실제 25,23,30 -> 13,12,16 ->27,24->32
                    Elements trList = elements.select("tr:nth-child(" +j+ ")");
                    Elements eachPost = trList.select("td");
//                    System.out.println(ele ments.select("td").size()/14 +":size ");
                    if(eachPost.size() ==1){
                        String plusName = eachPost.get(0).text();
                        firstNum = (firstNum+1)%2;
                    }
                    else{
                        String grade = eachPost.get(0).text();
                        String num = eachPost.get(3).text();
                        String name = eachPost.get(4).text();
                        String professor = eachPost.get(9).text();
                        String date = eachPost.get(10).text();
                        String special = eachPost.get(12).text();
                        System.out.printf("학년: "+grade+ "/학수번호: "+ num);
                        System.out.printf("/과목이름: %-20s",name);
                        System.out.printf("/교수님: %3s",professor);
                        System.out.printf("/날짜: %-20s",date);
                        System.out.printf("/비고: %-20s",special);
                        System.out.println();
                        Subject subject= new Subject(grade, num, special, professor, name, date);
                        subjectRepository.save(subject);
                    }
                }
                catch(IndexOutOfBoundsException e){
                    System.out.println("인덱스 오바");
                }
                finally{
                    continue;
                }
            }
            driver.navigate().back();
        }
        driver.close();
    }
    public void toCS(WebDriver driver){

        driver.findElement(By.cssSelector("#main > div.section.i2 > div.vp.q1 > div.pane.quick-links > div.content > div.member-links > div > div > div.tab.q1.current > div.content > div.front-quick-links-list > div > a.unit.q1")).click();
        String nextWindow = driver.getWindowHandle();
        driver.switchTo().window(nextWindow);
        driver.findElement(By.cssSelector("#menu > ul > li.on > div"));
        driver.findElement(By.cssSelector("#menu > ul > li.on > ul > li:nth-child(2) > a"));
    }
    public void login(WebDriver driver) throws InterruptedException{
        driver.get(urlHongik);

        driver.findElement(By.cssSelector("#main > div.h1.content > div.pane.login > div.content > div > table > tbody > tr > td:nth-child(2) > div > form > div > div > div.c.q1 > div.id > input[type=text]")).sendKeys("B935276");
        driver.findElement(By.cssSelector("#main > div.h1.content > div.pane.login > div.content > div > table > tbody > tr > td:nth-child(2) > div > form > div > div > div.c.q1 > div.pw > input[type=password]")).sendKeys("imagineyou12!");
        driver.findElement(By.cssSelector("#main > div.h1.content > div.pane.login > div.content > div > table > tbody > tr > td:nth-child(2) > div > form > div > div > div.c.q2 > button")).click();
        Thread.sleep(2000);
        driver.switchTo().alert().accept();
        driver.switchTo().window(MainWindow);
    }
    public void loginSugang(WebDriver driver) throws InterruptedException{
        driver.get(urlHongikSugang);
        Thread.sleep(2000);
        driver.findElement(By.cssSelector("body > table > tbody > tr:nth-child(2) > td > table > tbody > tr > td:nth-child(1) > table:nth-child(2) > tbody > tr:nth-child(2) > td > table > tbody > tr:nth-child(1) > td:nth-child(2) > input")).sendKeys("B935276");
        driver.findElement(By.cssSelector("body > table > tbody > tr:nth-child(2) > td > table > tbody > tr > td:nth-child(1) > table:nth-child(2) > tbody > tr:nth-child(2) > td > table > tbody > tr:nth-child(3) > td:nth-child(2) > input")).sendKeys("imagineyou12!");
        driver.findElement(By.cssSelector("body > table > tbody > tr:nth-child(2) > td > table > tbody > tr > td:nth-child(1) > table:nth-child(2) > tbody > tr:nth-child(2) > td > table > tbody > tr:nth-child(4) > td > input")).click();
    }
    public void findComputerData(WebDriver driver, int grade)throws  InterruptedException{
        driver.findElement(By.cssSelector("body > table > tbody > tr:nth-child(2) > td > table > tbody > tr > td:nth-child(1) > table:nth-child(2) > tbody > tr:nth-child(1) > td > table > tbody > tr:nth-child(10) > td > a")).click();
        Thread.sleep(2000);
        WebElement element =driver.findElement(By.cssSelector("body > table > tbody > tr:nth-child(2) > td > table > tbody > tr > td:nth-child(3) > div:nth-child(2) > table > tbody > tr > td > table:nth-child(3) > tbody > tr:nth-child(7) > td:nth-child(3) > form > select"));
        Select select = new Select(element);
        select.selectByIndex(grade);
    }
}
