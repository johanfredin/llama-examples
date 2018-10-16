package com.github.johanfredin.llama.bean;

public class MockItem implements LlamaBean {

    private String itemNo;
    private String name;

    public MockItem(String itemNo, String name) {
        this.itemNo = itemNo;
        this.name = name;
    }

    public String getItemNo() {
        return itemNo;
    }

    public void setItemNo(String itemNo) {
        this.itemNo = itemNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getId() {
        return this.itemNo;
    }
}
