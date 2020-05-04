package com.akashcp.windchimeslab.model;

public class Model {
    //college=zcZCxzczxcc, date=caczczc, cost=daasdasdsad, sex=sdasdasdad, name=ddsasdsad, phonenumber=dasdasdasdas

  //  String college,date,cost, sex,name, phonenumber;//,domain
    String phonenumber,name,sex,cost,date,college,domain,mcolor,paidcheckbox;
    Integer registernumber;
    String email;

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getMcolor() {
        return mcolor;
    }

    public void setMcolor(String mcolor) {
        this.mcolor = mcolor;
    }

    public String getPaidcheckbox() {
        return paidcheckbox;
    }

    public void setPaidcheckbox(String paidcheckbox) {
        this.paidcheckbox = paidcheckbox;
    }

    public Integer getRegisternumber() {
        return registernumber;
    }

    public void setRegisternumber(Integer registernumber) {
        this.registernumber = registernumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Model(String phonenumber, String name, String sex, String cost, String date, String college, String domain, String mcolor, String paidcheckbox, Integer registernumber, String email) {
        this.phonenumber = phonenumber;
        this.name = name;
        this.sex = sex;
        this.cost = cost;
        this.date = date;
        this.college = college;
        this.domain = domain;
        this.mcolor = mcolor;
        this.paidcheckbox = paidcheckbox;
        this.registernumber = registernumber;
        this.email = email;
    }

    public Model()
    {}


}
