package se.fredin.llama.mock.exchange;

import org.apache.camel.*;
import org.apache.camel.spi.Synchronization;
import org.apache.camel.spi.UnitOfWork;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class MockExchange implements Exchange {

    private MockMessage message = new MockMessage();

    @Override
    public ExchangePattern getPattern() {
        return null;
    }

    @Override
    public void setPattern(ExchangePattern pattern) {

    }

    @Override
    public Object getProperty(String name) {
        return null;
    }

    @Override
    public Object getProperty(String name, Object defaultValue) {
        return null;
    }

    @Override
    public <T> T getProperty(String name, Class<T> type) {
        return null;
    }

    @Override
    public <T> T getProperty(String name, Object defaultValue, Class<T> type) {
        return null;
    }

    @Override
    public void setProperty(String name, Object value) {

    }

    @Override
    public Object removeProperty(String name) {
        return null;
    }

    @Override
    public boolean removeProperties(String pattern) {
        return false;
    }

    @Override
    public boolean removeProperties(String pattern, String... excludePatterns) {
        return false;
    }

    @Override
    public Map<String, Object> getProperties() {
        return null;
    }

    @Override
    public boolean hasProperties() {
        return false;
    }

    @Override
    public Message getIn() {
        return this.message;
    }

    @Override
    public Message getMessage() {
        return this.message;
    }

    @Override
    public <T> T getMessage(Class<T> type) {
        return null;
    }

    @Override
    public void setMessage(Message message) {
    }

    @Override
    public <T> T getIn(Class<T> type) {
        return (T) this.message;
    }

    @Override
    public void setIn(Message in) {

    }

    @Override
    public Message getOut() {
        return null;
    }

    @Override
    public <T> T getOut(Class<T> type) {
        return null;
    }

    @Override
    public boolean hasOut() {
        return false;
    }

    @Override
    public void setOut(Message out) {

    }

    @Override
    public Exception getException() {
        return null;
    }

    @Override
    public <T> T getException(Class<T> type) {
        return null;
    }

    @Override
    public void setException(Throwable t) {

    }

    @Override
    public boolean isFailed() {
        return false;
    }

    @Override
    public boolean isTransacted() {
        return false;
    }

    @Override
    public Boolean isExternalRedelivered() {
        return null;
    }

    @Override
    public boolean isRollbackOnly() {
        return false;
    }

    @Override
    public CamelContext getContext() {
        return null;
    }

    @Override
    public Exchange copy() {
        return null;
    }

    @Override
    public Exchange copy(boolean safeCopy) {
        return null;
    }

    @Override
    public Endpoint getFromEndpoint() {
        return null;
    }

    @Override
    public void setFromEndpoint(Endpoint fromEndpoint) {

    }

    @Override
    public String getFromRouteId() {
        return null;
    }

    @Override
    public void setFromRouteId(String fromRouteId) {

    }

    @Override
    public UnitOfWork getUnitOfWork() {
        return null;
    }

    @Override
    public void setUnitOfWork(UnitOfWork unitOfWork) {

    }

    @Override
    public String getExchangeId() {
        return null;
    }

    @Override
    public void setExchangeId(String id) {

    }

    @Override
    public void addOnCompletion(Synchronization onCompletion) {

    }

    @Override
    public boolean containsOnCompletion(Synchronization onCompletion) {
        return false;
    }

    @Override
    public void handoverCompletions(Exchange target) {

    }

    @Override
    public List<Synchronization> handoverCompletions() {
        return null;
    }

    @Override
    public Date getCreated() {
        return null;
    }
}
