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
import com.github.johanfredin.llama.processor.Processors;
import com.github.johanfredin.llama.utils.Endpoint;
import org.springframework.stereotype.Component;

@Component
public class Ex7_MergeCollections extends LlamaRoute implements LlamaExamples {

    @Override
    public void configure() {
        var personRoute = super.getRoute("persons", exInputDir(), "person.csv", "person", nextAvailableStartup());

        // Merge with person-pets route
        from(Endpoint.file(exInputDir(), "pet-person.csv"))
                .autoStartup(LlamaExamplesApplication.AUTO_START_ROUTES)
                .unmarshal(super.csvToCollectionOfMaps())
                .pollEnrich(personRoute, (e1, e2) -> Processors.merge(e1, e2,true))
                .marshal(csvToCollectionOfMaps())
                .to(Endpoint.file(exOutputDir(), "persons-merged.csv"))
                .startupOrder(nextAvailableStartup())
                .onCompletion()
                .log(getCompletionMessage());
    }

    @Override
    public String exInputDir() {
        return prop("in-ex-7-merge-collection");
    }

    @Override
    public String exOutputDir() {
        return prop("out-ex-7-merge-collection");
    }
}
