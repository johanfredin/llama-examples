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
import com.github.johanfredin.llama.pojo.Fields;
import com.github.johanfredin.llama.pojo.JoinType;
import com.github.johanfredin.llama.pojo.Keys;
import com.github.johanfredin.llama.processor.Processors;
import org.apache.camel.model.RouteDefinition;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class Ex4_JoinCollections extends LlamaRoute implements LlamaExamples {

    @Override
    public void configure() {
        var routeId = exampleRouteId("read-persons");
        var csvToMapsFormat = csvToCollectionOfMaps();

        var petRoute = getRoute(exampleRouteId("read-pets"), exInputDir(),
                "pet.csv", "ex-4-join-collections-pets", nextAvailableStartup(), LlamaExamplesApplication.AUTO_START_ROUTES);

        from(file(exInputDir(), "person.csv"))
                .routeId(routeId)
                .autoStartup(LlamaExamplesApplication.AUTO_START_ROUTES)
                .unmarshal(csvToMapsFormat)
                .pollEnrich(petRoute.getEndpointUri(), (me, je) -> Processors.join(me, je, Keys.of("id"), JoinType.INNER, Fields.ALL, Fields.of(Map.of("type", "animal")), true))
                .marshal(csvToMapsFormat)
                .to(file(exOutputDir(), resultingFileName("csv")), controlBus(routeId))
                .startupOrder(nextAvailableStartup())
                .onCompletion().log(getCompletionMessage());

        endRoutes(petRoute);
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
