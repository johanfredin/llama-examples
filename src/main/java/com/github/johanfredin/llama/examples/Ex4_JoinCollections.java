package com.github.johanfredin.llama.examples;

import com.github.johanfredin.llama.LlamaExamplesApplication;
import com.github.johanfredin.llama.LlamaRoute;
import com.github.johanfredin.llama.pojo.Fields;
import com.github.johanfredin.llama.pojo.JoinType;
import com.github.johanfredin.llama.pojo.Keys;
import com.github.johanfredin.llama.processor.Processors;
import com.github.johanfredin.llama.utils.Endpoint;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class Ex4_JoinCollections extends LlamaRoute implements LlamaExamples {

    @Override
    public void configure() {
        var csvToMapsFormat = csvToCollectionOfMaps();

        var petRoute = getRoute(exampleRouteId("read-pets"), exInputDir(),
                "pet.csv", "ex-4-join-collections-pets", nextAvailableStartup(), LlamaExamplesApplication.AUTO_START_ROUTES);

        from(Endpoint.file(exInputDir(), "person.csv"))
                .routeId(exampleRouteId("read-persons"))
                .autoStartup(LlamaExamplesApplication.AUTO_START_ROUTES)
                .unmarshal(csvToMapsFormat)
                .pollEnrich(petRoute, (me, je) -> Processors.join(me, je, Keys.of("id"), JoinType.INNER, Fields.ALL, Fields.of(Map.of("type", "animal")), true))
                .marshal(csvToMapsFormat)
                .to(Endpoint.file(exOutputDir(), resultingFileName("csv")))
                .startupOrder(nextAvailableStartup())
                .onCompletion().log(getCompletionMessage());
    }

    @Override
    public String exInputDir() {
        return prop("in-ex-4-join-collections");
    }

    @Override
    public String exOutputDir() {
        return prop("out-ex-4-join-collections");
    }
}
