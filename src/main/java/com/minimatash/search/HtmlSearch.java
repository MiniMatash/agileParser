package com.minimatash.search;

import org.jsoup.nodes.Element;

import java.io.IOException;

public interface HtmlSearch {

    Element searchForElement (String originPath, String samplePath, String elementId) throws IOException;

}
