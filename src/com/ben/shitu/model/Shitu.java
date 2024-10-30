package com.ben.shitu.model;

import java.io.Serializable;

public class Shitu implements Serializable, Comparable<Shitu> {
    private long id;

    private int te_id = 0;

    private int stu_id = 0;

    private String te_name;

    private String stu_name;

    private int te_level;

    private int stu_level;

    private String time;

    private String chuangong;

    public Shitu(long id, int te_id, int stu_id, String te_name, String stu_name, int te_level, int stu_level, String time, String chuangong) {
        super();
        this.id = id;
        this.te_id = te_id;
        this.stu_id = stu_id;
        this.te_name = te_name;
        this.stu_name = stu_name;
        this.te_level = te_level;
        this.stu_level = stu_level;
        this.time = time;
        this.chuangong = chuangong;
    }

    public String getChuangong() {
        return chuangong;
    }

    public void setChuangong(String chuangong) {
        this.chuangong = chuangong;
    }

    public int getTe_level() {
        return te_level;
    }

    public void setTe_level(int te_level) {
        this.te_level = te_level;
    }

    public int getStu_level() {
        return stu_level;
    }

    public void setStu_level(int stu_level) {
        this.stu_level = stu_level;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTe_name() {
        return te_name;
    }

    public void setTe_name(String te_name) {
        this.te_name = te_name;
    }

    public String getStu_name() {
        return stu_name;
    }

    public void setStu_name(String stu_name) {
        this.stu_name = stu_name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getTe_id() {
        return te_id;
    }

    public void setTe_id(int te_id) {
        this.te_id = te_id;
    }

    public int getStu_id() {
        return stu_id;
    }

    public void setStu_id(int stu_id) {
        this.stu_id = stu_id;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (id ^ (id >>> 32));
        result = prime * result + stu_id;
        result = prime * result + te_id;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        final Shitu other = (Shitu) obj;
        if (id != other.id) return false;
        if (stu_id != other.stu_id) return false;
        return te_id == other.te_id;
    }

    public int compareTo(Shitu o) {
        if (o.getId() == id && o.getStu_id() == stu_id && o.getTe_id() == te_id) return 0;
        else return -1;
    }

}
