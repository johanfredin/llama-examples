package com.github.johanfredin.llama.examples;

import com.github.johanfredin.llama.LlamaExamplesApplication;
import com.github.johanfredin.llama.LlamaRoute;
import com.github.johanfredin.llama.processor.Processors;
import com.github.johanfredin.llama.utils.Endpoint;
import com.github.johanfredin.llama.utils.LlamaUtils;
import org.springframework.stereotype.Component;

@Component
public class Ex6_FilterCollection extends LlamaRoute implements LlamaExamples {

    @Override
    public void configure() {

        var mapFormat = super.csvToCollectionOfMaps();

        from(Endpoint.file(exInputDir(), "person.csv"))
                .routeId(exampleRouteId())
                .autoStartup(LlamaExamplesApplication.AUTO_START_ROUTES)
                .unmarshal(mapFormat)
                .process(exchange -> Processors.filterCollection(
                        exchange,
                        map -> LlamaUtils.withinRange(map.get("age"), 10, 30),
                        true))
                .marshal(mapFormat)
                .to(Endpoint.file(exOutputDir(), resultingFileName("csv")))
                .onCompletion()
                    .log(getCompletionMessage());
    }

    @Override
    public String exInputDir() {
        return prop("in-ex-6-filter-collection");
    }

    @Override
    public String exOutputDir() {
        return prop("out-ex-6-filter-collection");
    }
}
