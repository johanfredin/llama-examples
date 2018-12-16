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
import com.github.johanfredin.llama.utils.Endpoint;
import com.github.johanfredin.llama.utils.LlamaUtils;
import org.apache.camel.dataformat.bindy.csv.BindyCsvDataFormat;
import org.springframework.stereotype.Component;
import com.github.johanfredin.llama.examples.bean.Pet;
import com.github.johanfredin.llama.examples.bean.UserWithPets;

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
                .unmarshal(new BindyCsvDataFormat(UserWithPets.class))
                .pollEnrich("direct:ex2-2-csv-to1-pet", (oldExchange, newExchange) -> {
                    List<UserWithPets> userWithPets = LlamaUtils.asLlamaBeanList(oldExchange);
                    List<Pet> pets = LlamaUtils.asLlamaBeanList(newExchange);

                    // Create map <k, list<v>> of pets
                    var petMap = pets
                            .stream()
                            .collect(Collectors.groupingBy(Pet::getId));

                    // Match the 2
                    userWithPets.forEach(u -> {
                        var petList = petMap.get(u.getId());
                        if (petList != null) {
                            u.setPets(petList);
                        }
                    });

                    oldExchange.getIn().setBody(userWithPets);
                    return oldExchange;
                })
                .marshal(new BindyCsvDataFormat(UserWithPets.class))
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
        return prop("out-ex-2-2csv-to1");
    }
}
