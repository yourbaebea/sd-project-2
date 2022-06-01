package com.example.repository;

import com.example.data.Game;
import com.example.data.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface GameRepository extends CrudRepository<Game, Integer> {

    @Query("select g from Game g where (g.time >= ?1 and g.over = false) or (g.start = true and g.over = false and g.interrupted = false)")
    List<Game> getCurrentFutureGames(Timestamp currentTime);

    @Query("select g from Game g where g.start = true and g.over = false")
    List<Game> getCurrentGames();

    @Query("select CONCAT(g.result1, '-', g.result2) from Game g where g.id = ?1")
    String getGameResults(int id);

    @Transactional
    @Modifying
    @Query("UPDATE Game g SET g.result1 = g.result1 + 1 WHERE g.id = ?1")
    void updateScoreTeam1(int id);

    @Transactional
    @Modifying
    @Query("UPDATE Game g SET g.result2 = g.result2 + 1 WHERE g.id = ?1")
    void updateScoreTeam2(int id);

    @Transactional
    @Modifying
    @Query("UPDATE Game g SET g.over = true WHERE g.id = ?1")
    void updateOver(int id);


}    