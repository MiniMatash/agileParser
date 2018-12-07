package com.minimatash.check.impl;

import com.minimatash.check.ElementCheck;
import com.minimatash.entity.Candidate;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ElementCheckImpl implements ElementCheck {

    public Candidate elementsChecker(Elements candidates, Candidate candidate, Element criterion, Integer candidateSimilarity){
        for (Element elem : candidates) {
            Integer elementSimilarity = 0;

            if (elem.equals(candidate.getCandidate())) {
                continue;
            }

            for (Attribute attribute : elem.attributes()) {
                if (criterion.attributes().hasKey(attribute.getKey()) &&
                        criterion.attributes().get(attribute.getKey()).equals(attribute.getValue())) {
                    elementSimilarity++;
                }
            }

            if (checkElement(elementSimilarity, candidateSimilarity, elem, criterion)) {
                candidate.setCandidate(elem);
                candidate.setCandidateSimilarity(elementSimilarity);
            }
        }
        return candidate;
    }

    private Boolean checkElement(Integer elemSimilarity, Integer candidateSimilarity, Element element, Element criterion) {
        if (elemSimilarity >= candidateSimilarity) {

            if ((element.attributes().hasKey("class")) && (element.attributes().get("class").equals(criterion.attributes().get("class")))
                    || ((element.attributes().hasKey("id")) && (element.attributes().get("id").equals(criterion.attributes().get("id"))))) {
                return true;
            }
        }
        return false;
    }

}
