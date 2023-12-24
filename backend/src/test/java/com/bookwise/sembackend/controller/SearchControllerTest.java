    package com.bookwise.sembackend.controller;

import com.alibaba.fastjson2.JSONArray;
import com.bookwise.sembackend.controller.v1.SearchController;
import com.bookwise.sembackend.elastic_search.ESBook;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

    @SpringBootTest
public class SearchControllerTest {
    @Autowired
    private SearchController searchController;

    private String readFile(String filePath) {
        try {
            File file = new File(filePath);
            byte[] bytes = new byte[(int) file.length()];
            FileInputStream fileInputStream = new FileInputStream(filePath);
            int ret  = fileInputStream.read(bytes);
            String content = new String(bytes, 0, ret);
            return content;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    @Test
    public void test() {
        String content = readFile("JsonBookDownloader/proccessed_data.json");
        List<ESBook> books = JSONArray.parseArray(content, ESBook.class);
        System.out.println(books.size());
        assert !books.isEmpty();
        for (ESBook b : books) {
            searchController.addBook(b);
        }
        System.out.println("Insertion done");
    }

    @Test
    public void testSearch() {
        List<ESBook> books = searchController.deprecatedSearch("Harry", "");
        for (ESBook b : books) {
            System.out.println(b);
        }
    }
}
