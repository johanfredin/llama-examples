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
import com.github.johanfredin.llama.pojo.JoinType;
import com.github.johanfredin.llama.processor.Processors;
import com.github.johanfredin.llama.utils.Endpoint;
import org.apache.camel.dataformat.bindy.csv.BindyCsvDataFormat;
import org.springframework.stereotype.Component;
import com.github.johanfredin.llama.examples.bean.Pet;

@Component
public class Ex3_FilterValidateAgainst extends LlamaRoute implements LlamaExamples {

    public void configure() {
        String petRoute = getRoute(exampleRouteId("read-pets"), exInputDir(),
                "pet.csv", Pet.class, "ex-3-filter-validate-against-pets", nextAvailableStartup(), LlamaExamplesApplication.AUTO_START_ROUTES);

        from(Endpoint.file(exInputDir(), "person.csv"))
                .routeId(exampleRouteId("read-persons"))
                .autoStartup(LlamaExamplesApplication.AUTO_START_ROUTES)
                .unmarshal(new BindyCsvDataFormat(User.class))
                .pollEnrich(petRoute, (mainExchange, joiningExchange) -> Processors.<User, Pet>filterValidateAgainst(mainExchange, joiningExchange, JoinType.INNER))
                .marshal(new BindyCsvDataFormat(User.class))
                .to(Endpoint.file(exOutputDir(), resultingFileName("csv")))
                .startupOrder(nextAvailableStartup())
                .onCompletion().log(getCompletionMessage());
    }


    @Override
    public String exInputDir() {
        return prop("in-ex-3-filter-validate-against");
    }

    @Override
    public String exOutputDir() {
        return prop("out-ex-3-filter-validate-against");
    }
}
