package com.hongikScheduler.crawler;


import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
public class HongikCrawlerService {


    String MainWindow = new String();
    private static final String urlHongikSugang = "https://sugang.hongik.ac.kr/";
    private static final String urlHongik = "https://www.hongik.ac.kr/login.do";

    @PostConstruct
    public void getData() throws InterruptedException{

        Selenium selenium = new Selenium();
        WebDriver driver = selenium.getDriver();
        MainWindow = driver.getWindowHandle();
        loginSugang(driver);
        findCS3Grade(driver,2);

        ///Jsoup
        String html = driver.getPageSource();
        Document doc = Jsoup.parse(html);

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
    public void findCS3Grade(WebDriver driver,int grade)throws  InterruptedException{
        driver.findElement(By.cssSelector("body > table > tbody > tr:nth-child(2) > td > table > tbody > tr > td:nth-child(1) > table:nth-child(2) > tbody > tr:nth-child(1) > td > table > tbody > tr:nth-child(10) > td > a")).click();
        Thread.sleep(2000);
        WebElement element =driver.findElement(By.cssSelector("body > table > tbody > tr:nth-child(2) > td > table > tbody > tr > td:nth-child(3) > div:nth-child(2) > table > tbody > tr > td > table:nth-child(3) > tbody > tr:nth-child(7) > td:nth-child(3) > form > select"));
        Select select = new Select(element);
        select.selectByIndex(grade);

    }
}
