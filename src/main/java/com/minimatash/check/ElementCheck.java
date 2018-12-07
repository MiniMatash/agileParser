package com.minimatash.check;

import com.minimatash.entity.Candidate;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public interface ElementCheck {

    Candidate elementsChecker(Elements candidates, Candidate candidate, Element criterion, Integer candidateSimilarity);

}
