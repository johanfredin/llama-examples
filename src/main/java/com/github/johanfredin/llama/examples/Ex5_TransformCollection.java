package com.github.johanfredin.llama.examples;

import com.github.johanfredin.llama.LlamaExamplesApplication;
import com.github.johanfredin.llama.LlamaRoute;
import com.github.johanfredin.llama.processor.Processors;
import com.github.johanfredin.llama.utils.Endpoint;
import org.springframework.stereotype.Component;

@Component
public class Ex5_TransformCollection extends LlamaRoute implements LlamaExamples {

    @Override
    public void configure() {

        var csvMapFormat = super.csvToCollectionOfMaps();

        from(Endpoint.file(exInputDir(), "person.csv"))
                .routeId(exampleRouteId())
                .autoStartup(LlamaExamplesApplication.AUTO_START_ROUTES)
                .unmarshal(csvMapFormat)
                .process(exchange -> Processors.transformCollection(exchange, map -> {
                    map.put("age", "100");
                    map.put("first-name", "Transformed Name");
                }))
                .marshal(csvMapFormat)
                .to(Endpoint.file(exOutputDir(), resultingFileName("csv")))
                .onCompletion().log(getCompletionMessage());
    }

    @Override
    public String exInputDir() {
        return prop("in-ex-5-transform-collection");
    }

    @Override
    public String exOutputDir() {
        return prop("out-ex-5-transform-collection");
    }
}
