package com.example.demo;

import com.example.data.Player;
import com.example.data.Team;
import com.example.data.User;
import com.example.repository.TeamRepository;
import com.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.TabExpander;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service    
public class TeamService
{    
    @Autowired    
    private TeamRepository teamRepository;

    public List<Team> getAllTeams()
    {
        List<Team>teamRecords = new ArrayList<>();
        teamRepository.findAll().forEach(teamRecords::add);
        return teamRecords;
    }

    public boolean addTeam(Team team)
    {
        Optional<Team> t= teamRepository.findByName(team.getName());
        if(t.isPresent()){
            return false;
        }
        System.out.println(team);
        teamRepository.save(team);
        return true;
    }

    public boolean saveTeam(Team team)
    {
        teamRepository.save(team);
        return true;
    }


    public Optional<Team> getTeam(int id) {
        return teamRepository.findById(id);
    }

    public List<Team> teamsInGame(int id){
        return teamRepository.teamsInGame(id);
    }


}    