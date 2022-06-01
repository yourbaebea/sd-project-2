package com.example.data;

import java.util.List;
import java.util.Objects;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
@Table(name = "users") //idk???
@Entity
@XmlRootElement
public class User {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    String name;
    String phone;
    String password;
    String email;
    boolean admin;

    public User() {}
    
    public User(String name,String phone, String password,String email, boolean admin) {
        this.name = name;
        this.phone = phone;
        this.password = password;
        this.email = email;
        this.admin = admin;
    }

    public boolean login(String password) {
        return Objects.equals(password, this.password);
    }


    public boolean isAllowedToDoThis(int option) {
        //for the selected option do a switch
        if(option==1) return false;
        return false;
    }

    public int getId() {
        return id;
    }

    /*
    @XmlElementWrapper(name = "professors")
    @XmlElement(name = "prof")
    public List<Professor> getProfs() {
        return profs;
    }
    
    public void setProfs(List<Professor> profs) {
        this.profs = profs;
    }

    public void addProf(Professor prof) {
        this.profs.add(prof);
    }*/

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public String toString() {
        return this.name + "(id = " + this.id + "). admin: " + this.admin;
    }

}
