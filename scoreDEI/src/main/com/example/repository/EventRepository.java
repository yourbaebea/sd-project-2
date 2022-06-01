package com.example.repository;

import com.example.data.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EventRepository extends CrudRepository<Event, Integer> {

/*    //show the most recent ones first!
    @Query("SELECT e FROM Event e WHERE ( e.game_id = ?1 AND e.approved = true ) ORDER BY e.date DESC")
    Iterable<Event> findAllByGameId(Integer id);

    @Query(value = "SELECT e.player_id, COUNT(e.player_id) as c FROM Event e WHERE e.type = 7 GROUP BY e.player_id ORDER BY c DESC, e.player_id ASC")
    List<Object[]> findGoalCount();*/

    /*
    public interface ThingRepository extends JpaRepository<ThingEntity, Long> {

    @Query("SELECT t FROM Thing t WHERE t.fooIn = ?1 AND t.bar = ?2")
    ThingEntity findByFooInAndBar(String fooIn, String bar);
}
     */

    @Query("select e from Event e, Game g where e.game = g.id and g.id = ?1 ORDER BY e.time ASC")
    List<Event> eventsInGame(int id);
}