package com.groupeight.springscrapertest.scraper;

import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.Locale;

public class TaiwanPageJsoup {
    // obtain page cookies
    Connection.Response response;

    {
        try {
            response = Jsoup
                    .connect("http://www.esist.org.tw/Database/Search?PageId=0")
                    .method(Connection.Method.POST)
                    .execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    // fill in form attributes, invoke service
    Document serviceResponse;

    {
        try {
            serviceResponse = Jsoup
                    .connect("http://www.esist.org.tw/Database/List?PageId=0")
                    .userAgent("Chrome")
                    .cookies(response.cookies())
                    // calender, time, period and unit type selection
                    // calendar type
                    .data("yearType", "0")
                    // time period
                    .data("PeriodType", "M")
                    // start date Year 2017, Jan
                    .data("Start", "10601")
                    // end date Year 2021, June
                    // TODO get latest month
                    .data("End", "11007")
                    // unit type - original
                    .data("UnitType", "0")

                    // crude oil and petroleum products checkboxes
                    // check crude oil 原油
                    .data("EnergySelectedValue", "1_3_1")
                    // check liquefied petroleum gas 液化石油氣
                    .data("EnergySelectedValue", "1_3_5")
                    // check propane mixed gas (丙烷混合氣)
                    .data("EnergySelectedValue", "1_3_6")
                    // check natural gasoline 天然汽油
                    .data("EnergySelectedValue", "1_3_7")
                    // check naphtha 石油腦
                    .data("EnergySelectedValue", "1_3_8")
                    // check motor gasoline 車用汽油
                    .data("EnergySelectedValue", "1_3_9")
                    // check unleaded gasoline (無鉛汽油)
                    .data("EnergySelectedValue", "1_3_10")
                    // check aviation gasoline 航空汽油
                    .data("EnergySelectedValue", "1_3_11")
                    // check aviation fuel - gasoline type 航空燃油-汽油型
                    .data("EnergySelectedValue", "1_3_12")
                    // check aviation fuel - kerosene type 航空燃油-煤油型
                    .data("EnergySelectedValue", "1_3_13")
                    // check kerosene 煤油
                    .data("EnergySelectedValue", "1_3_14")
                    // check diesel 柴油
                    .data("EnergySelectedValue", "1_3_15")
                    // check fuel oil 燃料油
                    .data("EnergySelectedValue", "1_3_16")

                    // supply checkboxes
                    // check self-produced 自產
                    .data("FlowSelectedValue", "fl_1_1_1")
                    // check import 進口
                    .data("FlowSelectedValue", "fl_1_1_2")
                    // check export 進口
                    .data("FlowSelectedValue", "fl_1_1_3")

                    // check domestic energy consumption 國內能源消費
                    .data("FlowSelectedValue", "fl_1_3")
                    .post();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Write html to file, Open with Browser
        File file = new File("test.html");
        String docHtml = serviceResponse.html();
        try {
            Files.write(file.toPath(), docHtml.getBytes());
            Desktop.getDesktop().browse(file.toURI());
        } catch (IOException e) {

        }

        // convert each table into JSON
        JSONObject jsonParentObject = new JSONObject();
        // TODO repeat for all articles
        // using crude oil as example first
        Element productData = serviceResponse.getElementsByTag("article").first();
//            System.out.println(productData);
        // label name
        String arrayName = productData.select("h3").text();
//            System.out.println(arrayName);
        // get table in article
        Element table = productData.getElementsByTag("table").first();
        // get date with label at first
        Elements date = table.getElementsByAttribute("nowrap");
        // removing 日期
        date.remove(0);
        // System.out.println(date);
        // get table body
        Elements body = table.getElementsByTag("tbody");
        // for each <tr>
        JSONObject jsonObject = new JSONObject();
        for (Element row : body.select("tr")) {
            // get label of self-produced 自產, import 進口, export 出口, domestic energy consumption 國內能源消費
            String dataLabel = row.select("th").text();
            // get table values
            Elements tds = row.select("td");
            JSONObject dataJson = new JSONObject();
            // for each row in tds
            for (int i = 0; i < tds.size(); i++) {
                // date : data number
                int monthValue = Integer.parseInt((date.get(i).text().substring(5,7)));
                String monthString = Month.of(monthValue).getDisplayName(TextStyle.FULL_STANDALONE, Locale.ENGLISH);
                dataJson.put(date.get(i).text()
                                .replace("年", " ")
                                .replace("月", " " + monthString)
                                .replaceFirst(" [0-9]+ ", " "),
                        Integer.parseInt(tds.get(i).text().replaceAll(",", "")));
            }
            // TODO: sort JSON by Date
            jsonObject.put(dataLabel, dataJson);

        }
        jsonParentObject.put(arrayName, jsonObject);
        String prettyJson = jsonParentObject.toString(5);
        System.out.println(prettyJson);
    }
}

