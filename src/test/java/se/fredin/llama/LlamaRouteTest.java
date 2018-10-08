package se.fredin.llama;

import org.apache.camel.test.junit4.CamelTestSupport;

//@SpringBootTest
//@RunWith(SpringRunner.class)
public abstract class LlamaRouteTest extends CamelTestSupport {

//    @Autowired
//    protected SettingsComponent settingsComponent;
//
//    @Override
//    protected RoutesBuilder createRouteBuilder() {
//        return new JobRoute();
//    }
//
//    @Override
//    public boolean isUseAdviceWith() {
//        return true;
//    }
//
//    @Before
//    public void mockEndpoints() throws Exception {
//        for (MockRouteDefinition mockRouteDefinition : getMockRoutes()) {
//            AdviceWithRouteBuilder mockEndpoint = new AdviceWithRouteBuilder() {
//                @Override
//                public void configure() {
//                    for (EndpointToMockEndpoint etme : mockRouteDefinition.getEnpointsTomock()) {
//                        // Mock for testing
//                        interceptSendToEndpoint(etme.getRealEndpoint()).skipSendToOriginalEndpoint().to(etme.getMockedEnpoint());
//                    }
//                }
//            };
//            context.getRouteDefinition(mockRouteDefinition.getRouteId()).adviceWith(context, mockEndpoint);
//        }
//    }
//
//    public SettingsComponent getSettingsComponent() {
//        return settingsComponent;
//    }
//
//    public void setSettingsComponent(SettingsComponent settingsComponent) {
//        this.settingsComponent = settingsComponent;
//    }
//
//    protected abstract List<MockRouteDefinition> getMockRoutes();
}
