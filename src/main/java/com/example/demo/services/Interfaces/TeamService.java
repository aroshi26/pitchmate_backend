package com.example.demo.services.Interfaces;

import com.example.demo.Entities.Team;
import java.util.List;
import java.util.Optional;

public interface TeamService {

    List<Team> getAllTeams();

    Optional<Team> getTeamById(Integer teamId);

    Team createTeam(Team team);

    Team updateTeam(Integer teamId, Team teamDetails);

    void deleteTeam(Integer teamId);
}
