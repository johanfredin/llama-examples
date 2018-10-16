package com.github.johanfredin.llama.bean;

public class MockItemAsset implements LlamaBean {

    private String itemNo;
    private String quality;
    private String uncPath;

    public MockItemAsset(String id) {
        this(id, null, null);
    }

    public MockItemAsset(String itemNo, String quality, String uncPath) {
        this.itemNo = itemNo;
        this.quality = quality;
        this.uncPath = uncPath;
    }

    public String getItemNo() {
        return itemNo;
    }

    public void setItemNo(String itemNo) {
        this.itemNo = itemNo;
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public String getUncPath() {
        return uncPath;
    }

    public void setUncPath(String uncPath) {
        this.uncPath = uncPath;
    }

    @Override
    public String getId() {
        return this.itemNo;
    }
}
