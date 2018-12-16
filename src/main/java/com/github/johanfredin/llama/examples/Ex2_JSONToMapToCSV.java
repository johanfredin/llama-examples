package com.github.johanfredin.llama.examples;

import com.github.johanfredin.llama.LlamaExamplesApplication;
import com.github.johanfredin.llama.LlamaRoute;
import com.github.johanfredin.llama.utils.Endpoint;
import com.github.johanfredin.llama.utils.LlamaUtils;
import org.apache.camel.Exchange;
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

        from(Endpoint.file(exInputDir(), "person.json"))
                .autoStartup(LlamaExamplesApplication.AUTO_START_ROUTES)
                .unmarshal(format)
                .process(this::verifyIsMap)
                .marshal(format)
                .to(Endpoint.file(exOutputDir(), "person.csv"))
                .startupOrder(nextAvailableStartup())
                .onCompletion().log(getCompletionMessage());
    }

    private void verifyIsMap(Exchange e) {
        var listOfMaps = LlamaUtils.asListOfMaps(e);
        System.out.println("Entries\n--------");
        for(var entry : listOfMaps) {
            entry.entrySet().forEach(System.out::println);
            System.out.println();
        }
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
