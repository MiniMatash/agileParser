package com.minimatash;

import com.minimatash.search.HtmlSearch;
import com.minimatash.search.impl.HtmlSearchImpl;
import org.jsoup.nodes.Element;

public class HtmlParser {

    private static final HtmlSearch htmlSearch = new HtmlSearchImpl();

    public static void main(String[] args) {

        String originPath = args[0];
        String samplePath = args[1];
        String targetElementId = args[2];

        Element result = htmlSearch.searchForElement(originPath,samplePath,targetElementId);

        System.out.println(resultPathBuilder(result));
    }

    private static String resultPathBuilder(Element result) {

        StringBuilder elementPath = new StringBuilder().append(result.tagName());

        if (result.attributes().hasKey("class")) {
            elementPath.append("(class = ")
                    .append(result.attributes().get("class"))
                    .append(")");
        } else if (result.attributes().hasKey("id")) {
            elementPath.append("(id = ")
                    .append(result.attributes().get("id"))
                    .append(")");
        }

        while (result.hasParent()) {
            result = result.parent();
            elementPath.insert(0, ">");

            if (result.attributes().hasKey("class")) {
                elementPath.insert(0, ")")
                        .insert(0, result.attributes().get("class"))
                        .insert(0, "(class = ");
            } else if (result.attributes().hasKey("id")) {
                elementPath.insert(0, ")")
                        .insert(0, result.attributes().get("id"))
                        .insert(0, "(id = ");
            }

            elementPath.insert(0, result.tagName());
        }

        return elementPath.toString();
    }
}
