package com.gns.androidcourse.sqlitedatabase;

public class CustomerModal {
    private int id;
    private String name;
    private int age;
    private boolean isActive;
    private String namea;
    private int ageb;
    private boolean isActivec;

    public CustomerModal(int id, String name, int age, boolean isActive, String namea, int ageb, boolean isActivec) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.isActive = isActive;
        this.namea = namea;
        this.ageb = ageb;
        this.isActivec = isActivec;
    }

    @Override
    public String toString() {
        return "CustomerModal{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", isActive=" + isActive +
                ", namea='" + namea + '\'' +
                ", ageb=" + ageb +
                ", isActivec=" + isActivec +
                '}';
    }

    public boolean isActivec() {
        return isActivec;
    }

    public void setActivec(boolean activec) {
        isActivec = activec;
    }

    public int getAgeb() {
        return ageb;
    }

    public void setAgeb(int ageb) {
        this.ageb = ageb;
    }

    public String getNamea() {
        return namea;
    }

    public void setNamea(String namea) {
        this.namea = namea;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
