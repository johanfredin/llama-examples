package com.github.johanfredin.llama.examples;

import com.github.johanfredin.llama.LlamaRouteTest;

public class Ex1_CSVTest extends LlamaRouteTest {

//    @Override
//    protected List<MockRouteDefinition> getMockRoutes() {
//        return Arrays.asList(
//                new MockRouteDefinition("read-foo-csv",
//                        Arrays.asList(
//                                new EndpointToMockEndpoint(
//                                        LlamaUtils.file(getSettingsComponent().getOutputDirectory(), "foo_fixed.csv"),
//                                        "mock:foo_fixed")
//                        )));
//    }
//
//    @Test
//    public void testRoute() throws Exception {
//        MockEndpoint mockEndpoint = getMockEndpoint("mock:foo_fixed");
//
//        context.start();
//        mockEndpoint.expectedMessageCount(1);
//        mockEndpoint.assertIsSatisfied();
//        context.stop();
//    }
}