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
import com.github.johanfredin.llama.examples.bean.User;
import com.github.johanfredin.llama.utils.Endpoint;
import com.github.johanfredin.llama.utils.LlamaUtils;
import org.apache.camel.Exchange;
import org.apache.camel.dataformat.bindy.csv.BindyCsvDataFormat;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.stream.Collectors;

/**
 * Doing ex.1 where input/output is csv
 */
@Component
public class Ex1_CSV extends LlamaRoute implements LlamaExamples {

    @Override
    public void configure() {

        BindyCsvDataFormat bindyCsvDataFormat = new BindyCsvDataFormat(User.class);

        from(Endpoint.file(exInputDir(), "person.csv"))                                    // Fetch input file
                .autoStartup(LlamaExamplesApplication.AUTO_START_ROUTES)
                .routeId(exampleRouteId())
                .unmarshal(bindyCsvDataFormat)                                                   // Un-marshal CSV to POJO
                .process(this::processUsers)                                                     // Do transformation
                .marshal(bindyCsvDataFormat)                                                     // Marshal POJO back to CSV
                .to(Endpoint.file(exOutputDir(), resultingFileName("csv")))              // Write output file
                .onCompletion().log(getCompletionMessage());
    }

    private void processUsers(Exchange exchange) {
        var users = LlamaUtils.<User>asLlamaBeanList(exchange)
                .stream()                                                                       // Iterate users
                .filter(user -> LlamaUtils.withinRange(user.getAge(), 0, 100))        // Filter out invalid age
                .sorted(Comparator.comparing(User::getCountry))                              // Sort on country
                .peek(user -> user.setGender(user.getGender().toUpperCase()))                   // Set gender to be uppercase
                .collect(Collectors.toList());                                                  // Collect the update

        // Update the body
        exchange.getIn().setBody(users);
    }


    @Override
    public String exInputDir() {
        return prop("in-ex-1-csv");
    }

    @Override
    public String exOutputDir() {
        return prop("out-ex-1-csv");
    }

}
