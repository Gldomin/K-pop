package com.example.k_pop;

public class Artist {
    private String group;
    private String name;
    private boolean init = false;

    public Artist(String group, String name) {
        this.group = group;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getGroup() {
        return group;
    }

    public void setInit(boolean init) {
        this.init = init;
    }

    public void Init() {
        this.init = true;
    }

    public String getFolder() {
        return group + "/" + name;
    }

    public boolean isInit() {
        return init;
    }
}
