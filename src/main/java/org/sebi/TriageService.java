package org.sebi;


import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import io.smallrye.reactive.messaging.kafka.Record;
import io.quarkus.logging.Log;
import io.smallrye.common.annotation.Blocking;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class TriageService {

    @Inject
    AiService aiService;

    @Inject
    @Channel("negative-reviews")
    Emitter<TriagedReview> negativeEmitter;

    @Inject
    @Channel("positive-reviews")
    Emitter<TriagedReview> positiveEmitter;

    record Review(String review) {
    }

    @Incoming("reviews-in")
    @Blocking
    public void checkReview(Record<String, Review> message) {
        TriagedReview reviewAnalysis = aiService.checkReview(message.value().review());
        Log.info("Review analysis: " + reviewAnalysis);
        if (reviewAnalysis.evaluation() == Evaluation.POSITIVE){
            positiveEmitter.send(reviewAnalysis);
        } else {
            negativeEmitter.send(reviewAnalysis);
        }
    }
}
