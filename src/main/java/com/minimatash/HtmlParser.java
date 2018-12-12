package com.minimatash;

import com.minimatash.search.HtmlSearch;
import com.minimatash.search.impl.HtmlSearchImpl;
import org.apache.commons.cli.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.nodes.Element;

import java.io.IOException;

public class HtmlParser {

    private static final HtmlSearch htmlSearch = new HtmlSearchImpl();
    private static Logger logger = LogManager.getLogger();

    public static void main(String[] args) {


        Options options = new Options();
        options.addOption("of", true, "original file path");
        options.addOption("sf", true, "sample file path");
        options.addOption("tei", true, "target element id");

        CommandLineParser parser = new DefaultParser();
        try {
            CommandLine cmd = parser.parse(options, args);
            if(cmd.hasOption("of") && cmd.hasOption("sf") && cmd.hasOption("tei")) {
                String originPath = cmd.getOptionValue("of");
                String samplePath = cmd.getOptionValue("sf");
                String targetElementId = cmd.getOptionValue("tei");

                Element result = htmlSearch.searchForElement(originPath,samplePath,targetElementId);

                System.out.println(resultPathBuilder(result));
            } else {
                throw new IllegalArgumentException("Wrong options provided");
            }

        } catch (ParseException | IOException e) {
            logger.error(e.getMessage());
        }

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
