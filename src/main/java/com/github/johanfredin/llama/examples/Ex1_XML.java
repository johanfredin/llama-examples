/**
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
import org.apache.camel.Exchange;
import org.springframework.stereotype.Component;
import com.github.johanfredin.llama.examples.jaxb.Users;

import java.util.Comparator;
import java.util.stream.Collectors;

/**
 * Doing ex.1 where input/output is xml
 */
@Component
public class Ex1_XML extends LlamaRoute implements LlamaExamples {

    @Override
    public void configure() {
        from(Endpoint.file(exInputDir(), "person.xml"))
                .routeId(exampleRouteId())
                .autoStartup(LlamaExamplesApplication.AUTO_START_ROUTES)
                .convertBodyTo(Users.class)
                .process(this::processUsers)
                .marshal().jaxb()
                .to(Endpoint.file(exOutputDir(), resultingFileName("xml")))
                .onCompletion().log(getCompletionMessage());
    }

    private void processUsers(Exchange exchange) {
        var users = exchange.getIn().getBody(Users.class);
        users.setUsers(users.getUser()
                .stream()                                                       // Iterate users
                .filter(user -> user.getAge() > 0 && user.getAge() < 100)       // Filter out invalid age
                .sorted(Comparator.comparing(Users.User::getCountry))           // Sort on country
                .peek(user -> user.setGender(user.getGender().toUpperCase()))   // Set gender to be uppercase
                .collect(Collectors.toList()));                                 // Collect the update

        // Update the body
        exchange.getIn().setBody(users);
    }

    @Override
    public String exInputDir() {
        return prop("in-ex-1-xml");
    }

    @Override
    public String exOutputDir() {
        return prop("out-ex-1-xml");
    }
}
