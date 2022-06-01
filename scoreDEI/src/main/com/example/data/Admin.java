package com.example.data;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
public class Admin extends User {

    public Admin(String name, String phone, String password, String email, boolean admin) {
        super(name, phone, password, email, admin);
    }

    public Admin() {
    }

    //can an admin do certain things?
    @Override
    public boolean isAllowedToDoThis(int option) {
        return option == 1;
        //for rn
    }

}
