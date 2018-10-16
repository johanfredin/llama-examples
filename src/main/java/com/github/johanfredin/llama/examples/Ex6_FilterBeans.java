package com.github.johanfredin.llama.examples;

import com.github.johanfredin.llama.LlamaExamplesApplication;
import com.github.johanfredin.llama.LlamaRoute;
import com.github.johanfredin.llama.examples.bean.CsvUser;
import com.github.johanfredin.llama.processor.Processors;
import com.github.johanfredin.llama.utils.Endpoint;
import com.github.johanfredin.llama.utils.LlamaUtils;
import org.apache.camel.dataformat.bindy.csv.BindyCsvDataFormat;
import org.springframework.stereotype.Component;

@Component
public class Ex6_FilterBeans extends LlamaRoute implements LlamaExamples {

    @Override
    public void configure() {
        var bindyCsvDataFormat = new BindyCsvDataFormat(CsvUser.class);

        from(Endpoint.file(exInputDir(), "person.csv"))
                .routeId(exampleRouteId())
                .autoStartup(LlamaExamplesApplication.AUTO_START_ROUTES)
                .unmarshal(bindyCsvDataFormat)
                .process(exchange -> Processors.<CsvUser>filterBeans(exchange, user -> LlamaUtils.withinRange(user.getAge(), 20, 30)))
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
