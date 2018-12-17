package com.github.johanfredin.llama.examples;

import com.github.johanfredin.llama.LlamaExamplesApplication;
import com.github.johanfredin.llama.LlamaRoute;
import org.apache.camel.component.jackson.ListJacksonDataFormat;
import org.springframework.stereotype.Component;

/**
 * In this example we parse a JSON file to a list of maps instead of a Pojo.
 */
@Component
public class Ex2_JSONToMapToCSV extends LlamaRoute implements LlamaExamples {


    @Override
    public void configure() {

        var format = new ListJacksonDataFormat();

        from(file(exInputDir(), "person.json"))
                .routeId(exampleRouteId())
                .autoStartup(LlamaExamplesApplication.AUTO_START_ROUTES)
                .unmarshal(format)
                .process(exchange -> log.info("Exchange=" + exchange))
                .marshal(format)
                .to(file(exOutputDir(), resultingFileName("csv")), controlBus(exampleRouteId()))
                .startupOrder(nextAvailableStartup())
                .onCompletion().log(getCompletionMessage());
    }


    @Override
    public String exInputDir() {
        return prop("in-ex-2-json-to-map-to-csv");
    }

    @Override
    public String exOutputDir() {
        return prop("out-ex-2-json-to-map-to-csv");
    }

}
