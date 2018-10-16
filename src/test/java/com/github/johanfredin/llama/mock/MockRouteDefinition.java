package com.github.johanfredin.llama.mock;

import java.util.List;

public class MockRouteDefinition {

    private String routeId;
    private List<EndpointToMockEndpoint> enpointsTomock;

    public MockRouteDefinition(String routeId, List<EndpointToMockEndpoint> enpointsTomock) {
        this.routeId = routeId;
        this.enpointsTomock = enpointsTomock;
    }

    public String getRouteId() {
        return routeId;
    }

    public void setRouteId(String routeId) {
        this.routeId = routeId;
    }

    public List<EndpointToMockEndpoint> getEnpointsTomock() {
        return enpointsTomock;
    }

    public void setEnpointsTomock(List<EndpointToMockEndpoint> enpointsTomock) {
        this.enpointsTomock = enpointsTomock;
    }


    @Override
    public String toString() {
        return "MockRouteDefinition{" +
                "routeId='" + routeId + '\'' +
                ", enpointsTomock=" + enpointsTomock +
                '}';
    }
}
