package com.minimatash.entity;

import org.jsoup.nodes.Element;

public class Candidate {

    private Element candidate;

    private Integer candidateSimilarity;

    public Element getCandidate() {
        return candidate;
    }

    public void setCandidate(Element candidate) {
        this.candidate = candidate;
    }

    public Integer getCandidateSimilarity() {
        return candidateSimilarity;
    }

    public void setCandidateSimilarity(Integer candidateSimilarity) {
        this.candidateSimilarity = candidateSimilarity;
    }
}
