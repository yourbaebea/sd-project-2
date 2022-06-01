package com.example.data;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Entity
@XmlRootElement
public class Player {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    String name, position;
    String birthdate;
    String url;
    int goals;

    @ManyToOne
    @JoinColumn(name="team", nullable=true)
    Team team;



    public Player() {
    }

    public Player(int id,String name, String position, String birthdate, Team team) {
        this.id=id;
        this.name = name;
        this.position = position;
        this.birthdate=birthdate;
        this.team = team;
        this.goals=0;

    }

    public Player(int id, String name, String birthdate, String url) {
        this.name = name;
        this.position = position;
        this.birthdate= birthdate;
        this.url=url;
        this.goals=0;

    }




    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
        this.goals=0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setGoals(int goals) {
        this.goals = goals;
    }

    public int getGoals() {
        return goals;
    }

    public void addGoals() {
        this.goals++;
    }


}
