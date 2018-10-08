package se.fredin.llama.processor.join;

import org.junit.Test;
import se.fredin.llama.TestFixture;
import se.fredin.llama.bean.MockItem;
import se.fredin.llama.bean.MockItemAsset;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class FilterValidateAgainstBeansProcessorTest {

    private static final String MUTUAL_ID = TestFixture.getMockItem().getId();
    private static final String UNIQUE_ID_ITEMS = "UNIQUE_ID_ITEMS";
    private static final String UNIQUE_ID_ITEM_ASSETS = "UNIQUE_ID_ITEM_ASSETS";

    private Map<Object, List<MockItem>> mockItems = Map.of(
            MUTUAL_ID, List.of(TestFixture.getMockItem(), TestFixture.getMockItem(MUTUAL_ID, "Iphone")),
            UNIQUE_ID_ITEMS, List.of()
    );

    private Map<Object, List<MockItemAsset>> mockItemAssets = Map.of(
            MUTUAL_ID, List.of(TestFixture.getMockItemAsset()),
            UNIQUE_ID_ITEM_ASSETS, List.of(TestFixture.getMockItemAsset(UNIQUE_ID_ITEM_ASSETS, "Bad Quality", "Bad Path"))
    );

    @Test
    public void testInnerJoin() {
        var joinBeansProcessor = new <MockItem>FilterValidateAgainstBeansProcessor(JoinType.INNER);
        Map<Serializable, List<MockItem>> resultMap = joinBeansProcessor.filterValidateAgainst(mockItems, mockItemAssets);

        assertFalse("ResultMap should not contain entry with key=" + UNIQUE_ID_ITEMS, resultMap.keySet().contains(UNIQUE_ID_ITEMS));
        assertTrue("ResultMap contains all values associated with id", resultMap.get(MUTUAL_ID).size() == mockItems.get(MUTUAL_ID).size());
    }

    @Test
    public void testInnerJoinReversedExchanges() {
        var joinBeansProcessor = getProcessor(JoinType.INNER);
        Map<Serializable, List<MockItemAsset>> resultMap = joinBeansProcessor.filterValidateAgainst(mockItemAssets, mockItems);

        assertFalse("ResultMap should not contain entry with key=" + UNIQUE_ID_ITEM_ASSETS, resultMap.keySet().contains(UNIQUE_ID_ITEM_ASSETS));
        assertTrue("ResultMap contains all values associated with id", resultMap.get(MUTUAL_ID).size() == mockItemAssets.get(MUTUAL_ID).size());
    }

    @Test
    public void testLeftJoin() {

        var joinBeansProcessor = getProcessor(JoinType.LEFT_EXCLUDING);
        Map<Serializable, List<MockItem>> resultMap = joinBeansProcessor.filterValidateAgainst(mockItems, mockItemAssets);

        assertFalse("ResultMap should NOT have same amount of entries as mockItems map", resultMap.size() == mockItems.size());
        assertTrue("ResultMap contains key=" + UNIQUE_ID_ITEMS, resultMap.keySet().contains(UNIQUE_ID_ITEMS));
        assertFalse("ResultMap does not contains key=" + MUTUAL_ID, resultMap.keySet().contains(MUTUAL_ID));
    }

    @Test
    public void testLeftJoinReversedExchanges() {

        var joinBeansProcessor = getProcessor(JoinType.LEFT_EXCLUDING);
        Map<Serializable, List<MockItemAsset>> resultMap = joinBeansProcessor.filterValidateAgainst(mockItemAssets, mockItems);

        assertFalse("ResultMap should NOT have same amount of entries as mockItemAssets map", resultMap.size() == mockItemAssets.size());
        assertTrue("ResultMap contains key=" + UNIQUE_ID_ITEM_ASSETS, resultMap.keySet().contains(UNIQUE_ID_ITEM_ASSETS));
        assertFalse("ResultMap does not contains key=" + MUTUAL_ID, resultMap.keySet().contains(MUTUAL_ID));
    }

    private FilterValidateAgainstBeansProcessor getProcessor(JoinType type) {
        return new FilterValidateAgainstBeansProcessor(type);
    }

}