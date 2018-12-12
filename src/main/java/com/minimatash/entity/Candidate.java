package com.minimatash.entity;

import org.jsoup.nodes.Element;

public class Candidate {

    private Element candidate;

    private Double candidateSimilarity;

    public Element getCandidate() {
        return candidate;
    }

    public void setCandidate(Element candidate) {
        this.candidate = candidate;
    }

    public Double getCandidateSimilarity() {
        return candidateSimilarity;
    }

    public void setCandidateSimilarity(Double candidateSimilarity) {
        this.candidateSimilarity = candidateSimilarity;
    }

    public Candidate() {
        setCandidateSimilarity(0.0);
    }
}
