package com.example.formdata;

import com.example.data.Game;
import com.example.data.Team;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Timestamp;
import java.util.Optional;

public class FormGame {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;
    String place;
    String time;
    Timestamp time_format;
    Team team1;
    Team team2;

    public FormGame() {
    }


    public Game toGame(){
        Game g= new Game(this.place, this.team1,this.team2, this.time_format);
        g.setId(this.id);
        return g;
    }

    public void toForm(Game g){
        this.id= g.getId();
        this.time=g.getTime().toString();
        this.time_format=g.getTime();
        this.team1=g.getTeam1();
        this.team2=g.getTeam2();
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getPlace() {
        return place;
    }

    public void setTeam1(Team team1) {
        this.team1 = team1;
    }

    public Team getTeam1() {
        return team1;
    }

    public void setTeam2(Team team2) {
        this.team2 = team2;
    }

    public Team getTeam2() {
        return team2;
    }


    public void setTime(String time) {
        this.time = time;
        this.time_format = convert(time);
        System.out.println(time);
        System.out.println(this.time);
    }

    public Timestamp convert(String datetimelocal){
        if (StringUtils.countMatches(datetimelocal, ":") == 1) {
            datetimelocal += ":00";
        }
        return Timestamp.valueOf(datetimelocal.replace("T", " "));

    }

    public String getTime() {
        return time;
    }

    public Timestamp getTime_format() {
        return time_format;
    }

}




