package com.example.data;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;

@Entity
@XmlRootElement
public class Team {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    String name;
    String url;
    int wins;
    int loses;
    int draws;
    int total;

    @OneToMany(mappedBy="team")
    List<Player> player;



    public Team() {
    }

    public Team(String name, String url) {
        this.name = name;
        this.url = url;
        this.wins=0;
        this.loses=0;
        this.draws=0;
        this.total=0;

    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
        this.wins=0;
        this.loses=0;
        this.draws=0;
        this.total=0;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setPlayers(List<Player> players) {
        this.player = players;
    }

    public int getId() {
        return id;
    }

    //@XmlElementWrapper(name = "players")
    //@XmlElement(name = "player")
    public List<Player> getPlayers() {
        return player;
    }

    public int getDraws() {
        return draws;
    }

    public void setDraws(int draws) {
        this.draws = draws;
    }


    public void setLoses(int loses) {
        this.loses = loses;
    }

    public int getLoses() {
        return loses;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getWins() {
        return wins;
    }

    public void addDraws() {
        this.draws++;
    }

    public void addWins() {
        this.wins++;
    }

    public void addLoses() {
        this.loses++;
    }

    public void addTotal() {
        this.total++;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getTotal() {
        return total;
    }
}
