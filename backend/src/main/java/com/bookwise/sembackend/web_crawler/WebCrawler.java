package com.bookwise.sembackend.web_crawler;

import com.alibaba.fastjson2.JSON;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class WebCrawler {
    @Value("${api.rapidapi.url}")
    private String rapidApiUrl;

    @Value("${api.rapidapi.apikey}")
    private String rapidApiKey;

    public void crawlRapidApi() throws IOException, InterruptedException {
        String query = "";
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(rapidApiUrl + "?s=" + query))
                .header("X-RapidAPI-Key", rapidApiKey)
                .header("X-RapidAPI-Host", "getbooksinfo.p.rapidapi.com")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
    }
}
