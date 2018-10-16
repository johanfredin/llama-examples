package com.github.johanfredin.llama.examples.bean;

import com.github.johanfredin.llama.bean.LlamaBean;
import org.apache.camel.dataformat.bindy.annotation.CsvRecord;
import org.apache.camel.dataformat.bindy.annotation.DataField;

@CsvRecord(separator = ";", generateHeaderColumns = true, skipFirstLine = true)
public class Pet implements LlamaBean {

    @DataField(pos = 1)
    private long id;

    @DataField(pos = 2)
    private String name;

    @DataField(pos = 3)
    private String gender;

    @DataField(pos = 4)
    private String type;

    public Pet() {}

    public Pet(long id, String name, String gender, String type) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.type = type;
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Pet{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
