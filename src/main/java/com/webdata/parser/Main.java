package com.webdata.parser;

import com.webdata.parser.entities.Product;
import com.webdata.parser.parsers.ParserFromHtml;
import com.webdata.parser.parsers.ParserToXml;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sonik on 24.11.2017.
 */
public class Main {
    public static void main(String[] args) {

        ParserFromHtml parserFromHtml = new ParserFromHtml();
        ParserToXml parserToXml = new ParserToXml();

        long start = System.currentTimeMillis();

        System.out.println("In progress, please wait...\n");

        String searchUrl = "https://www.aboutyou.de/suche?category=&term=" + args[0];


        List<Product> productList = new ArrayList<>();
        try {
            List<String> urlList = parserFromHtml.getUrlList(searchUrl);
            productList = parserFromHtml.getProductList(urlList);
            parserToXml.jdomParseToXml(productList);
        } catch (IOException e) {
            e.printStackTrace();
        }


        long finish = System.currentTimeMillis();
        long runTime = (finish - start) / 1000;

        long memoryFootprint = (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024;

        System.out.println("Amount of triggered HTTP request = " + ParserFromHtml.amountHttpRequests);
        System.out.println("Run-time = " + runTime + " sec");
        System.out.println("Memory Footprint = " + memoryFootprint + " KB");
        System.out.println("Amount of extracted products = " + productList.size());

        System.out.println("\nDone!");

    }
}
