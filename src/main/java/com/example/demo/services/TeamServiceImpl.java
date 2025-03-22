package com.example.demo.services;

import com.example.demo.Entities.Team;
import com.example.demo.Repositories.TeamRepository;
import com.example.demo.services.Interfaces.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TeamServiceImpl implements TeamService {

    @Autowired
    private TeamRepository teamRepository;

    public List<Team> getAllTeams() {
        return teamRepository.findAll();
    }

    public Optional<Team> getTeamById(Integer teamId) {
        return teamRepository.findById(teamId);
    }

    public Team createTeam(Team team) {
        return teamRepository.save(team);
    }

    public Team updateTeam(Integer teamId, Team teamDetails) {
        return teamRepository.findById(teamId).map(existingTeam -> {
            existingTeam.setTeamName(teamDetails.getTeamName());
            return teamRepository.save(existingTeam);
        }).orElseThrow(() -> new RuntimeException("Team not found"));
    }

    public void deleteTeam(Integer teamId) {
        teamRepository.deleteById(teamId);
    }
}
