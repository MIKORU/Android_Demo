package com.example.sxs10540.uibean;

/**
 * Created by sxs10540 on 2017/7/26.
 */

public class Person {
    private String Name;
    private String Phone;
    private String Address;
    private String Email;

    public Person(String name, String phone) {
        Name = name;
        Phone = phone;
    }

    public String getName() {
        return Name;
    }

    public String getPhone() {
        return Phone;
    }

    public String getAddress() {
        return Address;
    }

    public String getEmail() {
        return Email;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public void setEmail(String email) {
        Email = email;
    }
}
