package com.example.repository;

import com.example.data.Team;
import com.example.data.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface TeamRepository extends CrudRepository<Team, Integer> {

    @Query("select t from Game g, Team t where (g.team1 = t.id or g.team2 = t.id) and g.id = ?1")
    List<Team> teamsInGame(int id);

    @Query("SELECT distinct t FROM Team t WHERE t.name=?1")
    Optional<Team> findByName(String name);

    //missing lost and tied games



}    