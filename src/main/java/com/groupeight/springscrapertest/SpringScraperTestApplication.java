package com.groupeight.springscrapertest;
import com.groupeight.springscrapertest.pages.TaiwanPage;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringScraperTestApplication {
    public static void main(String[] args) {
        TaiwanPage taiwanPage = new TaiwanPage();
        taiwanPage.startScraping();
    }
}
