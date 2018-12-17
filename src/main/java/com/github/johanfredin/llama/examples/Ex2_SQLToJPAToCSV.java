package com.github.johanfredin.llama.examples;

import com.github.johanfredin.llama.LlamaExamplesApplication;
import com.github.johanfredin.llama.LlamaRoute;
import com.github.johanfredin.llama.examples.bean.User;
import com.github.johanfredin.llama.utils.LlamaUtils;
import org.apache.camel.Exchange;
import org.apache.camel.dataformat.bindy.csv.BindyCsvDataFormat;
import org.springframework.stereotype.Component;

@Component
public class Ex2_SQLToJPAToCSV extends LlamaRoute implements LlamaExamples {

    @Override
    public void configure() {
        var routeId = exampleRouteId();
        var pojoClass = User.class;


        from(sql("select * from user", pojoClass))
                .routeId(routeId)
                .autoStartup(LlamaExamplesApplication.AUTO_START_SQL_ROUTES)
                .process(this::verifyContent)
                .marshal(new BindyCsvDataFormat(pojoClass))
                .to(file(exOutputDir(), resultingFileName("csv")), controlBus(routeId))
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
