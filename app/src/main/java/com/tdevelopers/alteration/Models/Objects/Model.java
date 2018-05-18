package com.tdevelopers.alteration.Models.Objects;

/**
 * Created by st185188 on 03/08/17.
 */

public class Model {
    public String name;
    public String pic;
    public String id;
    public String titlePic;
    public boolean itemFlag;

    public Model(String name, String pic, String id) {
        this.name = name;
        this.pic = pic;
        this.id = id;
    }

    public Model() {
    }
}
