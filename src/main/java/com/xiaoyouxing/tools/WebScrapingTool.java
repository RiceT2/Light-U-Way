package com.xiaoyouxing.tools;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class WebScrapingTool {

    public String scrape(String url) {
        try {
            Document document = Jsoup.connect(url).get();
            return document.title();
        } catch (Exception ex) {
            return "Error scraping page: " + ex.getMessage();
        }
    }
}
