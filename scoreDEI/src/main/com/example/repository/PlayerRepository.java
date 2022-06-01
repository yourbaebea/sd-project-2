package com.example.repository;

import com.example.data.Game;
import com.example.data.User;
import com.example.data.Player;
import com.example.data.Game;
import com.example.data.Player;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;
import java.util.Optional;

import java.util.List;

public interface PlayerRepository extends CrudRepository<Player, Integer> {

    @Query("SELECT distinct t.name, COUNT(t.name) FROM Game g, Team t WHERE g.over=true AND (g.team1=?1 OR g.team2=?1) GROUP BY t.name")
    List<Object> countTotalTeamGames();

    @Query("select distinct p from Player p, Game g, Team t where g.id = ?1 and (g.team1 = t.id or g.team2 = t.id) and p.team = t.id")
    List<Player> playersInGame(int id);

    @Query("select p from Player p where p.team.id = ?1")
    List<Player> playersInTeam(int id);

    @Query("select Max(p.goals) from Player p")
    int maxGoals();

    @Query("select p from Player p where p.goals = ?1")
    List<Player> playerMostGoals(int n);
}    