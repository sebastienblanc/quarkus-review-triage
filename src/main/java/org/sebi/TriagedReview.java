package org.sebi;

import com.fasterxml.jackson.annotation.JsonCreator;

public record TriagedReview(Evaluation evaluation, String message, String reply) {

    @JsonCreator
    public TriagedReview {
    }
    
}
