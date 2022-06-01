package com.example.data;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


/*-------------------------------- EVENT TYPE --------------------------------------------
    1- start of game
    2- end of game
    3- goal
    4- yellow
    5- red
    6- interrupted
    7- resumed
*/



@Entity
//@JsonIgnoreProperties({ "students" })
@XmlRootElement
public class Event {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private int type;
    private Timestamp time;

    @ManyToOne
    @JoinColumn(name="player", nullable=true)
    Player player;
    @ManyToOne
    @JoinColumn(name="team", nullable=true)
    Team team;
    @ManyToOne
    @JoinColumn(name="game", nullable=false)
    Game game;

    /*
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id")
    private Game g;

     */

    public Event() {}

    //1-4
    public Event(final int type, final Timestamp time, Game game) {
        this.type = type;
        this.time=time;
        this.game = game;
    }

    //5-6
    public Event(int type, Timestamp time, Player player_id, Game game) {
        this.type = type;
        this.time=time;
        this.player=  player_id;
        this.game = game;
    }

    //7
    public Event(int type, Timestamp time, Player player_id, Team team_id, Game game) {
        this.type = type;
        this.time=time;
        this.player=player_id;
        this.team=team_id;
        this.game = game;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

/*    @Override
    public String toString() {
        String str = "";
        switch (this.type) {
            case 1:
                str = "Game started: " + this.time;
                return str;
            case 2: str = "Game ended: " + this.time;
                return str;
            case 3: str = "Goal: " + this.time;
                return str;
            case 4: str = "Yellow card: " + this.time;
                return str;
            case 5: str = "Red card: " + this.time;
                return str;
            case 6: str = "Game interrupted: " + this.time;
                return str;
            case 7: str = "Game restarted: " + this.time;
                return str;
            default: str = "Error in event";
                return str;
        }*/



}
