package com.minimatash.check.impl;

import com.minimatash.check.ElementCheck;
import com.minimatash.entity.Candidate;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ElementCheckImpl implements ElementCheck {

    public Candidate elementsChecker(Elements candidates, Candidate candidate, Element criterion) {
        for (Element elem : candidates) {
            Double elementSimilarity = 0.0;

            if (elem.equals(candidate.getCandidate())) {
                continue;
            }

            for (Attribute attribute : elem.attributes()) {
                if (criterion.attributes().hasKey(attribute.getKey())) {
                    elementSimilarity += evaluateAttributeSimilarity(criterion.attributes().get(attribute.getKey()), attribute.getValue());
                }
            }

            if (elementSimilarity >= candidate.getCandidateSimilarity()) {
                if(candidate.getCandidate() !=null
                        && candidate.getCandidate().attr("class").contains(criterion.attr("class"))
                        && !elem.attr("class").contains(criterion.attr("class"))) {
                    continue;
                }

                candidate.setCandidate(elem);
                candidate.setCandidateSimilarity(elementSimilarity);
            }
        }
        return candidate;
    }

    private Double evaluateAttributeSimilarity(String criterion, String element) {
        String[] values = criterion.split(" ");
        Double attributeSimilarity = 0.0;
        for (int i = 0; i < values.length; i++) {
            if (element.contains(values[i])) {
                attributeSimilarity += 1.0 / values.length;
            }
        }
        return attributeSimilarity;
    }

}
