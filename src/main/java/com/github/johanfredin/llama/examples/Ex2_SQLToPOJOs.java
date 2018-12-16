package com.github.johanfredin.llama.examples;

import com.github.johanfredin.llama.LlamaRoute;
import com.github.johanfredin.llama.utils.Endpoint;
import org.apache.camel.Exchange;
import org.springframework.stereotype.Component;

@Component
public class Ex2_SQLToPOJOs extends LlamaRoute implements LlamaExamples{

    @Override
    public void configure() {
        from("sql:select * from user?useIterator=false&routeEmptyResultSet=true")
                .autoStartup(true)
                .process(this::verifyContent)
                .marshal(csvToCollectionOfMaps())
                .to(Endpoint.file(exOutputDir(), "sql-users.csv"))
                .onCompletion().log(getCompletionMessage());
    }

    private void verifyContent(Exchange exchange) {
        Object body = exchange.getIn().getBody();
        System.out.println("body=" + body);
    }

    @Override
    public String exInputDir() {
        return null;
    }

    @Override
    public String exOutputDir() {
        return null;
    }
}
