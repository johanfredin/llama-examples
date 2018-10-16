package com.github.johanfredin.llama.examples;

import com.github.johanfredin.llama.LlamaRoute;
import com.github.johanfredin.llama.utils.Endpoint;
import com.github.johanfredin.llama.utils.LlamaUtils;
import org.apache.camel.Exchange;
import org.apache.camel.dataformat.bindy.csv.BindyCsvDataFormat;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.stereotype.Component;
import com.github.johanfredin.llama.examples.bean.Pet;
import com.github.johanfredin.llama.examples.bean.User;

import java.util.stream.Collectors;

/**
 * Read 2 csv files and filterValidateAgainst them into one json file
 */
@Component
public class Ex2_2CSVTo1JSON extends LlamaRoute implements LlamaExamples {


    @Override
    public void configure() {

        from(Endpoint.file(exInputDir(), "pet.csv"))
                .routeId("pets")
                .unmarshal(new BindyCsvDataFormat(Pet.class))
                .to("direct:ex-2-2-csv-to1JSON-pet")
                .startupOrder(nextAvailableStartup());

        from(Endpoint.file(exInputDir(), "person.csv"))
                .routeId("users")
                .unmarshal(new BindyCsvDataFormat(User.class))
                .pollEnrich("direct:ex-2-2-csv-to1JSON-pet", this::aggregate)
                .marshal().json(JsonLibrary.Jackson)
                .to(Endpoint.file(exOutputDir(), resultingFileName("json")))
                .startupOrder(nextAvailableStartup())
                .onCompletion().log(getCompletionMessage());
    }


    private Exchange aggregate(Exchange oldExchange, Exchange newExchange) {

        // Create map <k, list<v>> of pets
        var petMap = LlamaUtils.<Pet>asLlamaBeanList(newExchange).stream()
                .collect(Collectors.groupingBy(Pet::getId));


        // Match the 2 (JRE8 way)
        var users = LlamaUtils.<User>asLlamaBeanList(oldExchange)
                .stream()
                .peek(u -> u.setPets(petMap.get(u.getId())))
                .collect(Collectors.toList());

        oldExchange.getIn().setBody(users);
        return oldExchange;
    }

    @Override
    public String exInputDir() {
        return prop("in-ex-2-2csv-to1JSON");
    }

    @Override
    public String exOutputDir() {
        return prop("out-ex-2-2csv-to1JSON");
    }
}
