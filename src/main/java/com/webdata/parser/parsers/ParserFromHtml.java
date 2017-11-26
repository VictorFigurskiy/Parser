package com.webdata.parser.parsers;

import com.webdata.parser.entities.Description;
import com.webdata.parser.entities.Product;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sonik on 25.11.2017.
 */
public class ParserFromHtml {

    private static final String WEBSITE = "https://www.aboutyou.de";
    public static int amountHttpRequests = 0;

    /**
     * The method takes a search URL , and then collects the URLs of the products in the list.
     * In addition, the presence of the next page is checked and, if it exists,
     * recursively collects data on the following pages.
     *
     * @param searchUrl
     * @return List of URLs which need to parse
     * @throws IOException
     */
    public List<String> getUrlList(String searchUrl) throws IOException {
        List<String> urlList = new ArrayList<>();

        Document document = Jsoup.connect(searchUrl).get();  // try to add .timeout3000

        amountHttpRequests++;

        Elements urlElements = document.getElementsByClass("js-productlist-product productlist-product isLayout3   ");

        for (Element urlElement : urlElements) {
            Element urlElem = urlElement.child(2).child(0);
            urlList.add(WEBSITE + urlElem.attr("href"));
        }


        Elements elementsByClass = document.getElementsByClass("btn btn-category-next");
        String nextPage = elementsByClass.attr("href");

        if (!nextPage.isEmpty()) {
            List<String> nextPageUrls = getUrlList(WEBSITE + nextPage);
            urlList.addAll(nextPageUrls);
        }

        return urlList;
    }


    public List<Product> getProductList(List<String> urlList) throws IOException {
        List<Product> productList = new ArrayList<>();

        for (String s : urlList) {
            productList.add(getProduct(s));
            amountHttpRequests++;
        }

        return productList;
    }


    /**
     * The method parses the HTML-page on the given fields and forms an object
     *
     * @param url
     * @return returns the filled Product object
     * @throws IOException
     */
    private Product getProduct(String url) throws IOException {

        Document doc = Jsoup.connect(url).get();

        Elements initialPriceElement = doc.getElementsByClass("originalPrice_17gsomb-o_O-strikeOut_32pxry");
        Elements priceElement = doc.getElementsByClass("finalPrice_klth9m-o_O-highlight_1t1mqn4");
        Elements brandAndNameElement = doc.getElementsByClass("productName_192josg");
        Elements articleAndDescriptionElement = doc.getElementsByClass("wrapper_1w5lv0w");
        Elements descriptionElement = doc.getElementsByClass("orderedList_1q5u47i");
        Elements shippingCostsElement = doc.getElementsByClass("headline_1crhtoo");

        String initialPrice = getTextFromElement(initialPriceElement);
        String price = getTextFromElement(priceElement);

        if (price == null || initialPrice == null) {
            Elements finalPrice = doc.getElementsByClass("finalPrice_klth9m");
            price = initialPrice = getTextFromElement(finalPrice);
        }
        if (price == null) {
            Elements priceElem = doc.getElementsByClass("price_1bdwo21");
            price = getTextFromElement(priceElem);
        }
        if (initialPrice == null) {
            Elements initPriceElem = doc.getElementsByClass("beforePrice_17gsomb-o_O-strikeOut_32pxry");
            initialPrice = getTextFromElement(initPriceElem);
        }

        String shippingCosts = shippingCostsElement.get(1).text();

        String data = doc.data();
        String[] split1 = data.split("\"color\":\"");
        String[] split2 = split1[1].split("\",\"image\"");
        String color = split2[0];

        String brandAndName = getTextFromElement(brandAndNameElement);

        String[] splitBrandName = null;
        if (brandAndName != null) {
            splitBrandName = brandAndName.split("\\|");
        }

        String brand = "";
        String name = "";

        if (splitBrandName != null && splitBrandName.length != 0) {
            brand = splitBrandName[0].trim();
            name = splitBrandName[1].trim();
        }

        Description description = getDescription(descriptionElement);

        String article = "";

        for (Element element : articleAndDescriptionElement) {
            String text = element.child(0).text();
            String[] splitArticleArray = text.split("Artikel-Nr: ");
            article = splitArticleArray[1];
        }


        return new Product(name, brand, color, price, initialPrice, article, shippingCosts, description);
    }


    private String getTextFromElement(Elements elements) {
        String value = null;

        for (Element element : elements) {
            value = element.text();
        }

        return value;
    }


    private Description getDescription(Elements descriptionElement) {
        List<String> designList = new ArrayList<>();
        List<String> extrasList = new ArrayList<>();
        List<String> materialCompositionList = new ArrayList<>();
        List<String> careInstructionsList = new ArrayList<>();
        List<String> sizeList = new ArrayList<>();


        for (int i = 0; i < descriptionElement.size(); i++) {

            List<String> descriptionList = descriptionElement.get(i).getElementsByTag("li").eachText();

            for (String description : descriptionList) {
                switch (i) {
                    case 0:
                        designList.add(description);
                        break;
                    case 1:
                        extrasList.add(description);
                        break;
                    case 2:
                        materialCompositionList.add(description);
                        break;
                    case 3:
                        careInstructionsList.add(description);
                        break;
                    case 4:
                        sizeList.add(description);
                        break;
                }
            }
        }
        return new Description(designList, extrasList, materialCompositionList, careInstructionsList, sizeList);
    }
}
