package com.github.johanfredin.llama.examples;

import com.github.johanfredin.llama.LlamaExamplesApplication;
import com.github.johanfredin.llama.LlamaRoute;
import com.github.johanfredin.llama.utils.Endpoint;
import com.github.johanfredin.llama.utils.LlamaUtils;
import org.apache.camel.dataformat.bindy.csv.BindyCsvDataFormat;
import org.springframework.stereotype.Component;
import com.github.johanfredin.llama.examples.bean.Pet;
import com.github.johanfredin.llama.examples.bean.User;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Read 2 csv files and filterValidateAgainst them into one
 */
@Component
public class Ex2_2CSVTo1 extends LlamaRoute implements LlamaExamples {

    @Override
    public void configure() {

        from(Endpoint.file(exInputDir(), "pet.csv"))
                .routeId(exampleRouteId("read-pets"))
                .autoStartup(LlamaExamplesApplication.AUTO_START_ROUTES)
                .unmarshal(new BindyCsvDataFormat(Pet.class))
                .to("direct:ex2-2-csv-to1-pet")
                .startupOrder(nextAvailableStartup());

        from(Endpoint.file(exInputDir(), "person.csv"))
                .routeId(exampleRouteId("read-persons"))
                .autoStartup(LlamaExamplesApplication.AUTO_START_ROUTES)
                .unmarshal(new BindyCsvDataFormat(User.class))
                .pollEnrich("direct:ex2-2-csv-to1-pet", (oldExchange, newExchange) -> {
                    List<User> users = LlamaUtils.asLlamaBeanList(oldExchange);
                    List<Pet> pets = LlamaUtils.asLlamaBeanList(newExchange);

                    // Create map <k, list<v>> of pets
                    var petMap = pets
                            .stream()
                            .collect(Collectors.groupingBy(Pet::getId));

                    // Match the 2
                    users.forEach(u -> {
                        var petList = petMap.get(u.getId());
                        if (petList != null) {
                            u.setPets(petList);
                        }
                    });

                    oldExchange.getIn().setBody(users);
                    return oldExchange;
                })
                .marshal(new BindyCsvDataFormat(User.class))
                .to(Endpoint.file(exOutputDir(), resultingFileName("csv")))
                .startupOrder(nextAvailableStartup())
                .onCompletion().log(getCompletionMessage());


    }

    @Override
    public String exInputDir() {
        return prop("in-ex-2-2csv-to1");
    }

    @Override
    public String exOutputDir() {
        return prop("out-ex2-2csv-to1");
    }
}
