package org.sebi;

import java.util.concurrent.TimeUnit;

import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.faulttolerance.Timeout;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import io.quarkiverse.langchain4j.RegisterAiService;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
@RegisterAiService
public interface AiService {
    
    @SystemMessage("you are a bot checking if the review is positive or negative. You will always answer with a JSON document, and only this JSON document.")
    @UserMessage("""
        Your task is to process the review delimited by ---.
        Apply a sentiment analysis to the passed review to determine if it is positive or negative.
        The review can be in any language. So, you will need to identify the language.

        For example:
        - "I love your product, you are the best!", this is a POSITIVE review
        - "J'adore votre produit", this is a POSITIVE review
        - "I hate your bank, you are the worst!", this is a NEGATIVE review

        Answer with a JSON document containing:
        - the 'evaluation' key set to 'POSITIVE' if the review is positive, 'NEGATIVE' otherwise, depending if the review is positive or negative
        - the 'reply' key set to a message thanking the customer in the case of a positive review, or an apology and a note that the bank is going to contact the customer in the case of a negative review. These messages must be polite and use the same language as the passed review.
        - the 'message' key set to the passed review

        ---
        {review}
        ---
        """)
    @Timeout(60000)
    @Retry(maxRetries = 3)
    public TriagedReview checkReview(String review);

}
