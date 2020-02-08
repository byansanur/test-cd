package com.byandev.io.testkerja.Model;


public class RespData {
    private	int	id;
    private String no_container;
    private String size;
    private String type;
    private String slots;
    private String rows;
    private String tier;

    public RespData(String no_container, String size, String type, String slots, String rows, String tier) {
        this.no_container = no_container;
        this.size = size;
        this.type = type;
        this.slots = slots;
        this.rows = rows;
        this.tier = tier;
    }

    public RespData(int id, String no_container, String size, String type, String slots, String rows, String tier) {
        this.id = id;
        this.no_container = no_container;
        this.size = size;
        this.type = type;
        this.slots = slots;
        this.rows = rows;
        this.tier = tier;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNo_container() {
        return no_container;
    }

    public void setNo_container(String no_container) {
        this.no_container = no_container;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSlots() {
        return slots;
    }

    public void setSlots(String slots) {
        this.slots = slots;
    }

    public String getRows() {
        return rows;
    }

    public void setRows(String rows) {
        this.rows = rows;
    }
    public String getTier() {
        return tier;
    }

    public void setTier(String tier) {
        this.tier = tier;
    }


}
