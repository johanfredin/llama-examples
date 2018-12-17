package com.github.johanfredin.llama.examples;

import com.github.johanfredin.llama.LlamaExamplesApplication;
import com.github.johanfredin.llama.LlamaRoute;
import com.github.johanfredin.llama.collection.LlamaMap;
import com.github.johanfredin.llama.utils.LlamaUtils;
import org.apache.camel.Exchange;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class Ex2_SQLToMapToCSV extends LlamaRoute implements LlamaExamples {

    @Override
    public void configure() {
        from(sql("select * from user"))
                .routeId(exampleRouteId())
                .autoStartup(LlamaExamplesApplication.AUTO_START_SQL_ROUTES)
                .process(exchange -> log.info("Exchange without header=" + exchange.getIn().getBody()))
                .process(LlamaUtils::reconnectHeader)
                .process(exchange -> log.info("Exchange with header=" + exchange.getIn().getBody()))
                .marshal(csvToCollectionOfMaps())
                .to(file(exOutputDir(), resultingFileName("csv")), controlBus(exampleRouteId()))
                .onCompletion().log(getCompletionMessage());
    }


    @Override
    public String exInputDir() {
        return null;
    }

    @Override
    public String exOutputDir() {
        return prop("out-ex-2-sql-to-map-to-csv");
    }
}
