package com.github.johanfredin.llama.examples;

import com.github.johanfredin.llama.LlamaRoute;
import com.github.johanfredin.llama.utils.Endpoint;
import org.apache.camel.Exchange;
import org.springframework.stereotype.Component;
import com.github.johanfredin.llama.examples.jaxb.Users;

import java.util.Comparator;
import java.util.stream.Collectors;

/**
 * Doing ex.1 where input/output is xml
 */
@Component
public class Ex1_XML extends LlamaRoute implements LlamaExamples {

    @Override
    public void configure() {
        from(Endpoint.file(exInputDir(), "foo.xml"))
                .convertBodyTo(Users.class)
                .process(this::processUsers)
                .marshal().jaxb()
                .to(Endpoint.file(exOutputDir(), resultingFileName("xml")))
                .onCompletion().log(getCompletionMessage());
    }

    private void processUsers(Exchange exchange) {
        var users = exchange.getIn().getBody(Users.class);
        users.setUsers(users.getUser()
                .stream()                                                       // Iterate users
                .filter(user -> user.getAge() > 0 && user.getAge() < 100)       // Filter out invalid age
                .sorted(Comparator.comparing(Users.User::getCountry))           // Sort on country
                .peek(user -> user.setGender(user.getGender().toUpperCase()))   // Set gender to be uppercase
                .collect(Collectors.toList()));                                 // Collect the update

        // Update the body
        exchange.getIn().setBody(users);
    }

    @Override
    public String exInputDir() {
        return prop("in-ex-1-xml");
    }

    @Override
    public String exOutputDir() {
        return prop("out-ex-1-xml");
    }
}
