# quarkus-review-triage

Largely inspired from the sample from the Quarkus Langchain4j extension, this application will consume a kakfa topic containing reviews. The LLM will decide if it's a positive or negative review and publish it on the correct topic. 

## Ollama and model

Make sure to have Ollama running and to have `mistral` model

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```shell script
./mvnw compile quarkus:dev
```
## Producing Reviews

The `microcks` devservice will be enabled by default and will start sending reviews (there are only 2 mocks , one positive and one negative). 
Go to your DevUI and check the Kafka Client extension, you should see after a while 2 new topics created `negative-reviews` and `postive-reviews` containing the triaged reviews.





