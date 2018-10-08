package se.fredin.llama.mock;

public class EndpointToMockEndpoint {

    private String realEndpoint;
    private String mockedEnpoint;

    public EndpointToMockEndpoint(String realEndpoint, String mockedEnpoint) {
        this.realEndpoint = realEndpoint;
        this.mockedEnpoint = mockedEnpoint;
    }

    public String getRealEndpoint() {
        return realEndpoint;
    }

    public void setRealEndpoint(String realEndpoint) {
        this.realEndpoint = realEndpoint;
    }

    public String getMockedEnpoint() {
        return mockedEnpoint;
    }

    public void setMockedEnpoint(String mockedEnpoint) {
        this.mockedEnpoint = mockedEnpoint;
    }

    @Override
    public String toString() {
        return "EndpointToMock{" +
                "realEndpoint='" + realEndpoint + '\'' +
                ", mockedEnpoint='" + mockedEnpoint + '\'' +
                '}';
    }
}
