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
import com.github.johanfredin.llama.collection.LlamaMap;
import com.github.johanfredin.llama.utils.Endpoint;
import com.github.johanfredin.llama.utils.LlamaUtils;
import org.apache.camel.Exchange;
import org.apache.camel.dataformat.csv.CsvDataFormat;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.HashMap;
import java.util.stream.Collectors;

/**
 * Perform ex_1 in this case with collections of lists as csv object
 * representations
 */
@Component
public class Ex1_CSVList extends LlamaRoute implements LlamaExamples {

    @Override
    public void configure() {
        var format = new CsvDataFormat();
        format.setDelimiter(';');
        format.setUseMaps(true);

        from(Endpoint.file(exInputDir(), "person.csv"))
                .routeId(exampleRouteId())
                .autoStartup(LlamaExamplesApplication.AUTO_START_ROUTES)
                .unmarshal(format)
                .process(this::transformData)
                .marshal(format)
                .to(Endpoint.file(exOutputDir(), resultingFileName("csv")))
                .onCompletion().log(getCompletionMessage());
    }

    private void transformData(Exchange exchange) {
        var collect = LlamaUtils.asLinkedListOfMaps(exchange)
                .stream()
                .filter(e -> LlamaUtils.withinRange(e.get("age"), 0, 100))
                .peek(e -> e.put("gender", e.get("gender").toUpperCase()))
                .sorted(Comparator.comparing(e -> e.get("country")))
                .collect(Collectors.toList());

        var header = new LlamaMap<String, String>();
        collect.get(0).keySet().forEach(e -> header.put(e, e));
        collect.add(0, header);
        exchange.getIn().setBody(collect);
    }

    @Override
    public String exInputDir() {
        return prop("in-ex-1-csv-list");
    }

    @Override
    public String exOutputDir() {
        return prop("out-ex-1-csv-list");
    }
}
