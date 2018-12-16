package com.github.johanfredin.llama.examples;

import com.github.johanfredin.llama.LlamaExamplesApplication;
import com.github.johanfredin.llama.LlamaRoute;
import com.github.johanfredin.llama.collection.LlamaMap;
import com.github.johanfredin.llama.utils.Endpoint;
import com.github.johanfredin.llama.utils.LlamaUtils;
import org.apache.camel.Exchange;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class Ex2_SQLToMapToCSV extends LlamaRoute implements LlamaExamples {

    @Override
    public void configure() {
        from("sql:select * from user?useIterator=false&routeEmptyResultSet=true&batch=true")
                .routeId(exampleRouteId())
                .autoStartup(LlamaExamplesApplication.AUTO_START_ROUTES)
                .process(this::verifyContent)
                .marshal(csvToCollectionOfMaps())
                .to(Endpoint.file(exOutputDir(), "sql-users.csv"))
                .to("controlbus:route?routeId=" + exampleRouteId() + "&action=stop&async=true")
                .onCompletion().log(getCompletionMessage());
    }

    private void verifyContent(Exchange exchange) {
        var body = LlamaUtils.asLinkedListOfMaps(exchange);
        body.add(0, header(body));
        System.out.println("body=" + body);
        exchange.getIn().setBody(body);
    }

    private Map<String, String> header(List<Map<String, String>> body) {
        var header = new LlamaMap<String, String>();
        body.get(0).keySet().forEach(key -> header.put(key, key));
        return header;
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
