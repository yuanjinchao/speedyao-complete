package com.speedyao;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class XuexiTest {
    public static void main(String[] args) throws InterruptedException {
        System.getProperties().setProperty("webdriver.chrome.driver","/Users/jinchao/work/chromedriver");
        WebDriver driver=new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.xuexi.cn");
        Thread.sleep(10*1000);
        System.out.println("打开网站");
        WebDriverWait wait = new WebDriverWait(driver, 120);
        wait.until(webDriver-> webDriver.findElement(By.xpath("//*[@id=\"root\"]/div/header/div[2]/div[2]/a[2]"))).click();
        System.out.println("等待扫码");
        wait.until(webDriver -> webDriver.findElement(By.xpath("//*[@id=\"qrcode\"]/img")));
        Thread.sleep(10*000);
        WebElement news_ele = driver.findElement(By.xpath("//*[@id=\"231c\"]/div/div/div/div/div/section/div/div/div/div/div[2]/section/div/div/div/div[1]"));
        List<WebElement> elements = news_ele.findElements(By.className("grid-cell"));
        for(WebElement e:elements){
            e.click();
            JavascriptExecutor js = (JavascriptExecutor)driver;
            Thread.sleep(3000);

        }
        String js1="document.documentElement.scrollTop=10000";

        String js2="document.documentElement.scrollTop=0";


    }
}
