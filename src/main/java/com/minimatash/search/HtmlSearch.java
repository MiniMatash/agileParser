package com.minimatash.search;

import org.jsoup.nodes.Element;

public interface HtmlSearch {

    Element searchForElement (String originPath, String samplePath, String elementId);

}
