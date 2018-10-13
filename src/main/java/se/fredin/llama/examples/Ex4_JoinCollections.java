package se.fredin.llama.examples;

import org.springframework.stereotype.Component;
import se.fredin.llama.LlamaRoute;
import se.fredin.llama.pojo.Fields;
import se.fredin.llama.pojo.JoinType;
import se.fredin.llama.pojo.Keys;
import se.fredin.llama.processor.Processors;
import se.fredin.llama.utils.Endpoint;

import java.util.Map;

@Component
public class Ex4_JoinCollections extends LlamaRoute implements LlamaExamples {

    @Override
    public void configure() {
        var csvToMapsFormat = csvToCollectionOfMaps();

        var petRoute = getRoute("pet-route", exInputDir(),
                "pet.csv", "ex-4-join-collections-pets", nextAvailableStartup());

        from(Endpoint.file(exInputDir(), "person.csv"))
                .routeId("person-route")
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
