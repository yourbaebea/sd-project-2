package com.example.app;

import com.example.data.Game;
import com.example.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service    
public class GameService
{    
    @Autowired    
    private GameRepository gameRepository;

    public List<Game> getAllGames()
    {
        List<Game>gameRecords = new ArrayList<>();
        gameRepository.findAll().forEach(gameRecords::add);
        return gameRecords;
    }


    public List<Game> getCurrentGames(){
        return gameRepository.getCurrentGames();
    }

    public boolean addGame(Game game)
    {
        if(game.getTeam1()!=game.getTeam2() && game.getTime().after(new Timestamp(System.currentTimeMillis())))
        {
            System.out.println(game);
            gameRepository.save(game);
            return true;
        }
        return false;

    }

    public boolean saveGame(Game game)
    {
        gameRepository.save(game);
        return true;

    }

    public Optional<Game> getGame(int id) {
        return gameRepository.findById(id);
    }

    public void updateScoreTeam1(int id){
        gameRepository.updateScoreTeam1(id);
    }

    public void updateScoreTeam2(int id){
        gameRepository.updateScoreTeam2(id);
    }

    public List<Game> getCurrentFutureGames(Timestamp currentTime){
        return gameRepository.getCurrentFutureGames(currentTime);
    }

    public void updateOver(int id){
        gameRepository.updateOver(id);
    }

    public String getGameResults(int id){
        return gameRepository.getGameResults(id);
    }


}    