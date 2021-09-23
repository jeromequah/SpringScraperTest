package com.groupeight.springscrapertest;
import com.groupeight.springscrapertest.scraper.TaiwanPageJsoup;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringScraperTestApplication {
    public static void main(String[] args) {
//        TaiwanPage taiwanPage = new TaiwanPage();
//        taiwanPage.startScraping();
        TaiwanPageJsoup taiwanPageJsoup = new TaiwanPageJsoup();
    }
}
