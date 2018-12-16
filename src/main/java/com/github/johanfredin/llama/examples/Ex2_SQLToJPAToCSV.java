package com.github.johanfredin.llama.examples;

import com.github.johanfredin.llama.LlamaRoute;
import com.github.johanfredin.llama.examples.bean.User;
import com.github.johanfredin.llama.utils.Endpoint;
import com.github.johanfredin.llama.utils.LlamaUtils;
import org.apache.camel.Exchange;
import org.apache.camel.dataformat.bindy.csv.BindyCsvDataFormat;
import org.springframework.stereotype.Component;

@Component
public class Ex2_SQLToJPAToCSV extends LlamaRoute implements LlamaExamples {

    @Override
    public void configure() {
        from("sql:select * from user?useIterator=false&routeEmptyResultSet=true&outputClass=com.github.johanfredin.llama.examples.bean.User")
                .routeId(exampleRouteId())
                .autoStartup(true)
                .process(this::verifyContent)
                .marshal(new BindyCsvDataFormat(User.class))
                .to(Endpoint.file(exOutputDir(), "sql-users.csv"), "controlbus:route?routeId=" + exampleRouteId() + "&action=stop&async=true")
                .onCompletion().log(getCompletionMessage());
    }

    private void verifyContent(Exchange exchange) {
        var body = LlamaUtils.<User>asLlamaBeanList(exchange);
        System.out.println("Body=" + body);
    }

    @Override
    public String exInputDir() {
        return null;
    }

    @Override
    public String exOutputDir() {
        return prop("out-ex-2-sql-to-pojo-to-csv");
    }
}
