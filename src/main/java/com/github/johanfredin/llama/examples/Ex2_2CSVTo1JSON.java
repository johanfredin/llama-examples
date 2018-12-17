/*
 * Copyright 2018 Johan Fredin
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.johanfredin.llama.examples;

import com.github.johanfredin.llama.LlamaExamplesApplication;
import com.github.johanfredin.llama.LlamaRoute;
import com.github.johanfredin.llama.examples.bean.Pet;
import com.github.johanfredin.llama.examples.bean.UserWithPets;
import com.github.johanfredin.llama.utils.LlamaUtils;
import org.apache.camel.Exchange;
import org.apache.camel.dataformat.bindy.csv.BindyCsvDataFormat;
import org.apache.camel.model.RouteDefinition;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

/**
 * Read 2 csv files and filterValidateAgainst them into one json file
 */
@Component
public class Ex2_2CSVTo1JSON extends LlamaRoute implements LlamaExamples {


    @Override
    public void configure() {

        RouteDefinition petRoute = from(file(exInputDir(), "pet.csv"))
                .routeId(exampleRouteId("read-pets"))
                .autoStartup(LlamaExamplesApplication.AUTO_START_ROUTES)
                .unmarshal(new BindyCsvDataFormat(Pet.class))
                .to("direct:ex-2-2-csv-to1JSON-pet")
                .startupOrder(nextAvailableStartup());

        from(file(exInputDir(), "person.csv"))
                .routeId(exampleRouteId("read-users"))
                .autoStartup(LlamaExamplesApplication.AUTO_START_ROUTES)
                .unmarshal(new BindyCsvDataFormat(UserWithPets.class))
                .pollEnrich("direct:ex-2-2-csv-to1JSON-pet", this::aggregate)
                .marshal().json(JsonLibrary.Jackson)
                .to(file(exOutputDir(), resultingFileName("json")), controlBus(exampleRouteId("read-users")))
                .startupOrder(nextAvailableStartup())
                .onCompletion().log(getCompletionMessage());

        petRoute.to(controlBus(petRoute.getId()));
    }


    private Exchange aggregate(Exchange oldExchange, Exchange newExchange) {

        // Create map <k, list<v>> of pets
        var petMap = LlamaUtils.<Pet>asLlamaBeanList(newExchange).stream()
                .collect(Collectors.groupingBy(Pet::getId));


        // Match the 2 (JRE8 way)
        var users = LlamaUtils.<UserWithPets>asLlamaBeanList(oldExchange)
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
