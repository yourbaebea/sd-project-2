package com.example.data;

import javax.persistence.*;
import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@XmlRootElement
public class Game {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    String place;
    Timestamp time;
    int result1, result2;
    boolean start, over, interrupted;

/*    @OneToMany(mappedBy="game")
    List<Event> event;*/
    @ManyToOne
    @JoinColumn(name="team1", nullable=false)
    Team team1;
    @ManyToOne
    @JoinColumn(name="team2", nullable=false)
    Team team2;

    public Game() {
    }

    public Game(String place, Team team1, Team team2, Timestamp time) {
        this.place = place;
        this.team1 = team1;
        this.team2 = team2;
        this.time=time;
        this.over=false;
        this.start = false;
        this.interrupted=false;
        this.result1=0;
        this.result2=0;
    }

/*    public void setEvents(List<Event> events) {
        this.event = events;
    }

    @XmlElementWrapper(name = "events")
    @XmlElement(name = "event")
    public List<Event> getEvents() {
        return event;
    }*/

    public String currentResult(){
        return this.team1 + " " + this.result1 + "-" + this.result2 + " " + this.team2;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
        this.over=false;
        this.start = false;
        this.interrupted=false;
        this.result1=0;
        this.result2=0;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public int getResult1() {
        return result1;
    }

    public void setResult1(int result1) {
        this.result1 = result1;
    }

    public int getResult2() {
        return result2;
    }

    public void setResult2(int result2) {
        this.result2 = result2;
    }

    public boolean isOver() {
        return over;
    }

    public void setOver(boolean over) {
        this.over = over;
    }

    public boolean isInterrupted() {
        return interrupted;
    }

    public void setInterrupted(boolean interrupted) {
        this.interrupted = interrupted;
    }

    public Team getTeam1() {
        return team1;
    }

    public void setTeam1(Team team1) {
        this.team1 = team1;
    }

    public Team getTeam2() {
        return team2;
    }

    public void setTeam2(Team team2) {
        this.team2 = team2;
    }


    public boolean isStart() {
        return start;
    }

    public void setStart(boolean start) {
        this.start = start;
    }
}
