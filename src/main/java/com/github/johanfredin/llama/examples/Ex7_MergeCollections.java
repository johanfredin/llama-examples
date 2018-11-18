package com.github.johanfredin.llama.examples;

import com.github.johanfredin.llama.LlamaRoute;
import com.github.johanfredin.llama.processor.Processors;
import com.github.johanfredin.llama.utils.Endpoint;
import org.springframework.stereotype.Component;

@Component
public class Ex7_MergeCollections extends LlamaRoute implements LlamaExamples {

    @Override
    public void configure() {
        var personRoute = super.getRoute("persons", exInputDir(), "person.csv", "person", nextAvailableStartup());

        // Merge with person-pets route
        from(Endpoint.file(exInputDir(), "pet-person.csv"))
                .unmarshal(super.csvToCollectionOfMaps())
                .pollEnrich(personRoute, (e1, e2) -> Processors.merge(e1, e2,true))
                .marshal(csvToCollectionOfMaps())
                .to(Endpoint.file(exOutputDir(), "persons-merged.csv"))
                .startupOrder(nextAvailableStartup())
                .onCompletion()
                .log(getCompletionMessage());
    }

    @Override
    public String exInputDir() {
        return prop("in-ex-7-merge-collection");
    }

    @Override
    public String exOutputDir() {
        return prop("out-ex-7-merge-collection");
    }
}
