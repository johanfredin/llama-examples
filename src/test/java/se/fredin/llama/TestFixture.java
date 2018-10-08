package se.fredin.llama;

import org.apache.camel.Exchange;
import se.fredin.llama.bean.LlamaBean;
import se.fredin.llama.bean.MockItem;
import se.fredin.llama.bean.MockItemAsset;
import se.fredin.llama.mock.exchange.MockExchange;

import java.util.Arrays;
import java.util.List;

public class TestFixture {

    public static final String DEFAULT_ITEM_NO = "Abc123";
    public static final String DEFAULT_NAME  = "Samsung Galaxy S8";
    public static final String DEFAULT_QUALITY = "Thumbnail";
    public static final String DEFAULT_PATH = "http://www.somepage/someasset.jpg";

    public static MockItem getMockItem() {
        return getMockItem(DEFAULT_ITEM_NO, DEFAULT_NAME);
    }

    public static MockItem getMockItem(String itemNo, String name) {
        return new MockItem(itemNo, name);
    }

    public static MockItemAsset getMockItemAsset() {
        return getMockItemAsset(DEFAULT_ITEM_NO, DEFAULT_QUALITY, DEFAULT_PATH);
    }

    public static MockItemAsset getMockItemAsset(String itemNo, String quality, String uncPath) {
        return new MockItemAsset(itemNo, quality, uncPath);
    }

    public static List<MockItem> getMockItems(MockItem... mockItems) {
        return Arrays.asList(mockItems);
    }

    public static List<MockItemAsset> getMockItemAssets(MockItemAsset... mockItemAssets) {
        return Arrays.asList(mockItemAssets);
    }

    public static <T extends LlamaBean> Exchange getMockExchange(List<T> mockBody) {
        var mockExchange = new MockExchange();
        mockExchange.getIn().setBody(mockBody);
        return mockExchange;
    }


}
