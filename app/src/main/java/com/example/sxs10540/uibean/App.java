package com.example.sxs10540.uibean;

/**
 * Created by sxs10540 on 2017/7/27.
 */

public class App {
    private String id;
    private String name;
    private String version;

    public App(String id, String name, String version) {
        this.id = id;
        this.name = name;
        this.version = version;
    }

    public App() {
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {

        this.version = version;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

}
