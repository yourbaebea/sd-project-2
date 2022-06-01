package com.example.app;

import com.example.data.Player;
import com.example.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PlayerService
{
    @Autowired
    private PlayerRepository playerRepository;

    public List<Player> getAllPlayers()
    {
        List<Player>playerRecords = new ArrayList<>();

        playerRepository.findAll().forEach(playerRecords::add);
        return playerRecords;
    }
    public void addPlayer(Player player){
        System.out.println(player);
        playerRepository.save(player);
    }


    public Optional<Player> getPlayer(int id) {
        return playerRepository.findById(id);
    }

    public List<Player> playersInGame(int id){
        return playerRepository.playersInGame(id);
    }
    public List<Player> playersInTeam(int id){
        return playerRepository.playersInTeam(id);
    }

    public int maxGoals(){
        return playerRepository.maxGoals();
    }

    public List<Player> playerMostGoals(int n){
        return playerRepository.playerMostGoals(n);
    }

}