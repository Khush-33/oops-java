package com.ipl.auction.data;

import com.ipl.auction.model.Player;
import com.ipl.auction.model.PlayerRole;
import com.ipl.auction.model.Team;

import java.util.ArrayList;
import java.util.List;

/**
 * A utility class to load initial mock data for the auction.
 * This abstracts away the data source. Currently, it's hardcoded,
 * but it could be modified to read from a file or database without
 * changing any other part of the application.
 */
public class DataLoader {

    public static List<Player> loadPlayers() {
        List<Player> players = new ArrayList<>();
        players.add(new Player(1, "Virat Kohli", "India", PlayerRole.BATSMAN, 20000000));
        players.add(new Player(2, "Rohit Sharma", "India", PlayerRole.BATSMAN, 18000000));
        players.add(new Player(3, "Jasprit Bumrah", "India", PlayerRole.BOWLER, 15000000));
        players.add(new Player(4, "Hardik Pandya", "India", PlayerRole.ALL_ROUNDER, 16000000));
        players.add(new Player(5, "MS Dhoni", "India", PlayerRole.WICKETKEEPER, 10000000));
        players.add(new Player(6, "Pat Cummins", "Australia", PlayerRole.BOWLER, 12000000));
        players.add(new Player(7, "Rashid Khan", "Afghanistan", PlayerRole.BOWLER, 14000000));
        players.add(new Player(8, "Ben Stokes", "England", PlayerRole.ALL_ROUNDER, 13000000));
        return players;
    }

    public static List<Team> loadTeams() {
        List<Team> teams = new ArrayList<>();
        teams.add(new Team(1, "Mumbai Indians", 90000000));
        teams.add(new Team(2, "Chennai Super Kings", 85000000));
        teams.add(new Team(3, "Royal Challengers Bangalore", 87000000));
        return teams;
    }
}
