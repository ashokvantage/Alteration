package com.tdevelopers.alteration.Models.Objects;

/**
 * Created by st185188 on 01/09/17.
 */

public class Banner {
    public String desc;

    public Banner(String id, String name, String desc, String pic) {
        this.id = id;
        this.name = name;
        this.pic = pic;
        this.desc = desc;
    }

    public String id;
    public String name;
    public String pic;

}
