package com.github.johanfredin.llama.examples.bean;

import com.github.johanfredin.llama.bean.LlamaBean;
import org.apache.camel.dataformat.bindy.annotation.CsvRecord;
import org.apache.camel.dataformat.bindy.annotation.DataField;

import java.util.List;

@CsvRecord(separator = ";", generateHeaderColumns = true, skipFirstLine = true)
public class User implements LlamaBean {

    @DataField(pos = 1)
    private long id;

    @DataField(pos = 2)
    private String firstName;

    @DataField(pos = 3)
    private String lastName;

    @DataField(pos = 4)
    private int age;

    @DataField(pos = 5)
    private String gender;

    @DataField(pos = 6)
    private String country;

    private List<Pet> pets;

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setPets(List<Pet> pets) {
        this.pets = pets;
    }

    public List<Pet> getPets() {
        return pets;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                ", gender='" + gender + '\'' +
                ", country='" + country + '\'' +
                ", pets=" + pets +
                '}';
    }


}
