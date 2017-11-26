package com.webdata.parser.parsers;

import com.webdata.parser.entities.Product;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created by Sonik on 25.11.2017.
 */
public class ParserToXml {

    private static final String FILENAME = "offers.xml";


    /**
     * The method performs the conversion from objects to XML file with help of JDOM
     *
     * @param productList - list of products for parsing
     */
    public void jdomParseToXml(List<Product> productList) {

        Element offers = new Element("offers");

        Document document = new Document(offers);

        for (Product product : productList) {
            Element offer = new Element("offer");
            offer.addContent(new Element("name").setText(product.getName()));
            offer.addContent(new Element("brand").setText(product.getBrand()));
            offer.addContent(new Element("color").setText(product.getColor()));
            offer.addContent(new Element("initial_price").setText(product.getInitialPrice()));
            offer.addContent(new Element("price").setText(product.getPrice()));
            offer.addContent(new Element("shipping_costs").setText(product.getShippingCosts()));
            offer.addContent(new Element("article_id").setText(product.getArticleID()));

            Element description = new Element("description_list");

            Element designList = elementFromList("design_list", product.getDescription().getDesignList());
            Element extrasList = elementFromList("extras_list", product.getDescription().getExtrasList());
            Element materialCompositionList = elementFromList("material_composition_list", product.getDescription().getMaterialCompositionList());
            Element careInstructionsList = elementFromList("care_instructions_list", product.getDescription().getCareInstructionsList());
            Element sizeList = elementFromList("size_list", product.getDescription().getSizeList());

            description.addContent(designList);
            description.addContent(extrasList);
            description.addContent(materialCompositionList);
            description.addContent(careInstructionsList);
            description.addContent(sizeList);

            offer.addContent(description);
            offers.addContent(offer);
        }

        XMLOutputter xmlOutputter = new XMLOutputter();
        xmlOutputter.setFormat(Format.getPrettyFormat());

        try {
            xmlOutputter.output(document, new FileOutputStream(
                    System.getProperty("user.dir") + File.separator + FILENAME));
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private Element elementFromList(String listTagName, List<String> list) {
        Element element = new Element(listTagName);

        for (int i = 0; i < list.size(); i++) {
            if (list.size() - 1 == i) {
                element.addContent(list.get(i));
            } else element.addContent(list.get(i) + ", ");
        }

        return element;
    }


}
