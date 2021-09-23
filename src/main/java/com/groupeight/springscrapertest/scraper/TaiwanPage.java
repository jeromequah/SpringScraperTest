package com.groupeight.springscrapertest.scraper;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaiwanPage {
    public void startScraping() {
        main(null);
    }

    public static void main(String[] args) {
        // Options for ChromeDriver
        ChromeOptions options = new ChromeOptions();

        // start screen maximized
        options.addArguments("start-maximized");
        // prefs Map object to add to options
        Map<String, Object> prefs = new HashMap<String, Object>();
        // download path
        prefs.put("download.default_directory", "D:\\SpringScraperTest\\src\\main\\java\\com\\groupeight\\springscrapertest\\lib");
        // language settings - ISO 639-1 language codes from Taiwan to English default
        prefs.put("translate_whitelists", new String[] {"zh-TW","en"});
        prefs.put("translate", new String[] {"enabled","true"});
        options.setExperimentalOption("prefs", prefs);

//      Web Driver - code's connection to browser window
        WebDriver driver = new ChromeDriver(options);
        WebDriverManager.chromedriver().setup();

//      Navigation to TW Webpage
        driver.navigate().to("http://www.esist.org.tw/Database/Search?PageId=0");

//      To synchronise actions, after x seconds will carry on
        WebDriverWait wait = new WebDriverWait(driver, 10);

        // Supply and Consumption

        /*
         * Change to gregorian calendar,  month type remain the same
         */

        // Address to point to element clicked - use Chrome Dev Tools Inspect XPath
        String gregorianCalenderPath = "//*[@id=\"yearType\"]/option[1]";
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(gregorianCalenderPath)));
        driver.findElement(By.xpath(gregorianCalenderPath)).click();

        /* Find this start month Select path tag,
         * get all Options tag under them,
         * Choose the earliest month / year and assign to startMonthPath ,
         * endMonth by default latest
         * */

        String startMonthPath = "//*[@id=\"ddl_s_m\"]";

        Select startSelect = new Select(driver.findElement(By.xpath(startMonthPath)));
        List<WebElement> allStartOptions = startSelect.getOptions();
        final String startDate = "10806"; //actual --> 10601 == Jan 2017
        //hardcode as that is the start based on requirements
        WebElement start = allStartOptions.stream().filter(o -> startDate.equals(o.getAttribute("value"))).findFirst().orElse(null);
//        System.out.println(start.getText());
        if (start == null) {
            System.out.println("Start cant be found");
            driver.close();
        } else {
            wait.until(ExpectedConditions.elementToBeClickable(start));
            start.click();
        }

        /*
         * Find Supply checkbox and click it,
         * Click on select all
         */
        String supplyPath = "//*[@id=\"rightBox\"]/article[1]/section/label";
        String supplySelectAllPath = "//*[@id=\"rightBox\"]/article[1]/div/div[1]/label";

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(supplyPath)));
        driver.findElement(By.xpath(supplyPath)).click();
//        System.out.println(driver.findElement(By.xpath(supplyPath)).getText());


        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(supplySelectAllPath)));
        driver.findElement(By.xpath(supplySelectAllPath)).click();
//        System.out.println(driver.findElement(By.xpath(supplySelectAllPath)).getText());


        /*
         * Find Crude oil and petroleum products checkbox,
         * Click on select all
         */

        String oilPath = "//*[@id=\"leftBox\"]/article[3]/section/label";
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(oilPath)));
        driver.findElement(By.xpath(oilPath)).click();
//        System.out.println(driver.findElement(By.xpath(oilPath)).getText());


        String oilSelectAllPath = "//*[@id=\"leftBox\"]/article[3]/div/div[1]/label";
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(oilSelectAllPath)));
        driver.findElement(By.xpath(oilSelectAllPath)).click();
//        System.out.println(driver.findElement(By.xpath(oilSelectAllPath)).getText());


        /*
         * Find Domestic energy consumption (), Click on select all
         */
        String domesticConsumptionPath = "//*[@id=\"rightBox\"]/article[3]/section/label";
        String domesticConsumptionSelectAllPath = "//*[@id=\"rightBox\"]/article[3]/div/div[1]/label";

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(domesticConsumptionPath)));
        driver.findElement(By.xpath(domesticConsumptionPath)).click();
//        System.out.println(driver.findElement(By.xpath(domesticConsumptionPath)).getText());


        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(domesticConsumptionSelectAllPath)));
        driver.findElement(By.xpath(domesticConsumptionSelectAllPath)).click();
//        System.out.println(driver.findElement(By.xpath(domesticConsumptionSelectAllPath)).getText());


        /*
         * Submit query
         */
        String submitButtonPath = "//*[@id=\"data_accordion\"]/div[2]/div[2]/button";
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(submitButtonPath)));
        driver.findElement(By.xpath(submitButtonPath)).click();

        /*
         * Download Excel
         */
        By downloadExcelPath = By.xpath("/html/body/div[1]/div/div/section/ul/li[1]/a");
        wait.until(ExpectedConditions.presenceOfElementLocated(downloadExcelPath)).click();
//        System.out.println(driver.findElement(downloadExcelPath).getAttribute("href"));
//        String url = driver.findElement(downloadExcelPath).getAttribute("href");
//        try {
//            URL urlCsv = new URL(url);
//            URLConnection urlConn = urlCsv.openConnection();
//            InputStreamReader inputCSV = new InputStreamReader(
//                    ((URLConnection) urlConn).getInputStream());
//            BufferedReader br = new BufferedReader(inputCSV);
//
//            System.out.println(br.readLine());
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        driver.quit();
    }
}
