package se.fredin.llama.examples;

import org.apache.camel.dataformat.bindy.csv.BindyCsvDataFormat;
import org.springframework.stereotype.Component;
import se.fredin.llama.LlamaRoute;
import se.fredin.llama.examples.bean.CsvUser;
import se.fredin.llama.examples.bean.Pet;
import se.fredin.llama.pojo.JoinType;
import se.fredin.llama.processor.Processors;
import se.fredin.llama.utils.Endpoint;

@Component
public class Ex3_FilterValidateAgainst extends LlamaRoute implements LlamaExamples {

    public void configure() {
        String petRoute = getRoute("read-pets", exInputDir(),
                "pet.csv", Pet.class, "ex-3-filter-validate-against-pets", nextAvailableStartup());

        from(Endpoint.file(exInputDir(), "person.csv"))
                .routeId("read-persons")
                .unmarshal(new BindyCsvDataFormat(CsvUser.class))
                .pollEnrich(petRoute, (mainExchange, joiningExchange) -> Processors.<CsvUser, Pet>filterValidateAgainst(mainExchange, joiningExchange, JoinType.INNER))
                .marshal(new BindyCsvDataFormat(CsvUser.class))
                .to(Endpoint.file(exOutputDir(), resultingFileName("csv")))
                .startupOrder(nextAvailableStartup())
                .onCompletion().log(getCompletionMessage());
    }


    @Override
    public String exInputDir() {
        return prop("in-ex-3-filter-validate-against");
    }

    @Override
    public String exOutputDir() {
        return prop("out-ex-3-filter-validate-against");
    }
}
