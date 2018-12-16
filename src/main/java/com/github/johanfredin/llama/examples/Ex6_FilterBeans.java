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
import com.github.johanfredin.llama.processor.Processors;
import com.github.johanfredin.llama.utils.Endpoint;
import com.github.johanfredin.llama.utils.LlamaUtils;
import org.apache.camel.dataformat.bindy.csv.BindyCsvDataFormat;
import org.springframework.stereotype.Component;

@Component
public class Ex6_FilterBeans extends LlamaRoute implements LlamaExamples {

    @Override
    public void configure() {
        var bindyCsvDataFormat = new BindyCsvDataFormat(User.class);

        from(Endpoint.file(exInputDir(), "person.csv"))
                .routeId(exampleRouteId())
                .autoStartup(LlamaExamplesApplication.AUTO_START_ROUTES)
                .unmarshal(bindyCsvDataFormat)
                .process(exchange -> Processors.<User>filterBeans(exchange, user -> LlamaUtils.withinRange(user.getAge(), 20, 30)))
                .marshal(bindyCsvDataFormat)
                .to(Endpoint.file(exOutputDir(), resultingFileName("csv")))
                .onCompletion().log(getCompletionMessage());
    }

    @Override
    public String exInputDir() {
        return prop("in-ex-6-filter-beans");
    }

    @Override
    public String exOutputDir() {
        return prop("out-ex-6-filter-beans");
    }
}
