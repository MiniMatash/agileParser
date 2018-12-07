package com.minimatash.search.impl;

import com.minimatash.check.ElementCheck;
import com.minimatash.check.impl.ElementCheckImpl;
import com.minimatash.search.HtmlSearch;
import com.minimatash.entity.Candidate;
import org.apache.logging.log4j.LogManager;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Optional;


public class HtmlSearchImpl implements HtmlSearch {

    private Logger logger = LogManager.getLogger();
    private static final String CHARSET_NAME = Charset.defaultCharset().name();
    private static final ElementCheck elementsChecker = new ElementCheckImpl();

    @Override
    public Element searchForElement(String originPath, String samplePath, String elementId) {

        Optional<Element> element = findElementById(new File(originPath), elementId);

        if (!element.isPresent()) {
            throw new IllegalArgumentException("Can't find element with id " + elementId + " in file " + originPath);
        }

        return recursiveSearch(samplePath, element.get().parent(), element.get(), new Candidate(), 0).getCandidate();
    }

    private Candidate recursiveSearch(String filePath, Element parent, Element criterion, Candidate candidate, Integer candidateSimilarity){
        for (Attribute criterionAttributes : criterion.attributes()) {

            String cssQuery = queryBuilder(criterion.parent(), criterion, criterionAttributes.getKey());
            if (cssQuery == null) {
                break;
            }

            Optional<Elements> candidates = findElementsByQuery(new File(filePath), cssQuery);
            if(!candidates.isPresent()){
                continue;
            }

            candidate = elementsChecker.elementsChecker(candidates.get(), candidate, criterion, candidateSimilarity);

        }
        if (parent.hasParent()) {
            candidate = recursiveSearch(filePath, parent.parent(), criterion, candidate, candidateSimilarity);
        }
        return candidate;
    }

    private String queryBuilder(Element parent, Element criterion, String sourceAttributeName) {
        StringBuilder cssQuery = new StringBuilder();

        cssQuery.append(parent.tag())
                .append("[");

        if (parent.attributes().hasKey("id ")) {
            cssQuery.append("id=\"")
                    .append(parent.attributes().get("id"))
                    .append("\"] ");
        } else if (parent.attributes().hasKey("class")) {
            cssQuery.append("class=\"")
                    .append(parent.attributes().get("class"))
                    .append("\"] ");
        } else {
            return null;
        }

        cssQuery.append(criterion.tagName())
                .append("[")
                .append(sourceAttributeName)
                .append("=\"")
                .append(criterion.attr(sourceAttributeName))
                .append("\"]");

        return cssQuery.toString();
    }

    private Optional<Elements> findElementsByQuery(File htmlFile, String cssQuery) {
        try {
            Document doc = Jsoup.parse(
                    htmlFile,
                    CHARSET_NAME,
                    htmlFile.getAbsolutePath());

            return Optional.ofNullable(doc.select(cssQuery));

        } catch (IOException e) {
            logger.error("Error reading file " + htmlFile.getAbsolutePath());
            System.exit(1);
        }
        return null;
    }

    private Optional<Element> findElementById(File htmlFile, String targetElementId) {
        try {
            Document doc = Jsoup.parse(
                    htmlFile,
                    CHARSET_NAME,
                    htmlFile.getAbsolutePath());

            return Optional.ofNullable(doc.getElementById(targetElementId));

        } catch (IOException e) {
            logger.error("Error reading file " + htmlFile.getAbsolutePath());
            System.exit(1);
        }
        return null;
    }


}
