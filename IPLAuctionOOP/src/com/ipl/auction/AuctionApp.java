package com.ipl.auction;

import com.ipl.auction.data.DataLoader;
import com.ipl.auction.model.Player;
import com.ipl.auction.model.Team;
import com.ipl.auction.service.AuctionService;

import java.util.List;
import java.util.Scanner;

/**
 * The main entry point for the IPL Auction console application.
 * This class is responsible for the user interface (command-line menu)
 * and orchestrating the different parts of the application.
 */
public class AuctionApp {

    public static void main(String[] args) {
        // Load initial data
        List<Player> players = DataLoader.loadPlayers();
        List<Team> teams = DataLoader.loadTeams();
        AuctionService auctionService = new AuctionService(players, teams);
        Scanner scanner = new Scanner(System.in);
        boolean auctionStarted = false;

        System.out.println("Welcome to the IPL Auction Management System!");

        while (true) {
            printMenu();
            System.out.print("Please enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    System.out.println("\n--- All Teams ---");
                    for (Team team : teams) {
                        System.out.println(team);
                    }
                    break;
                case 2:
                    System.out.println("\n--- Available Players for Auction ---");
                    for (Player player : players) {
                        if (player.getStatus() == com.ipl.auction.model.PlayerStatus.AVAILABLE) {
                            System.out.println(player);
                        }
                    }
                    break;
                case 3:
                    System.out.print("Enter Team ID to view squad: ");
                    int teamId = scanner.nextInt();
                    scanner.nextLine(); // consume newline
                    Team selectedTeam = findTeamById(teams, teamId);
                    if (selectedTeam != null) {
                        selectedTeam.printSquadDetails();
                    } else {
                        System.out.println("Team not found.");
                    }
                    break;
                case 4:
                    if (!auctionStarted) {
                        auctionService.startAuction();
                        auctionStarted = true;
                    } else {
                        System.out.println("Auction has already concluded.");
                    }
                    break;
                case 5:
                    System.out.println("Exiting application. Thank you!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void printMenu() {
        System.out.println("\n--- Main Menu ---");
        System.out.println("1. View All Teams");
        System.out.println("2. View Available Players");
        System.out.println("3. View Squad of a Specific Team");
        System.out.println("4. Start Auction");
        System.out.println("5. Exit");
    }
    
    private static Team findTeamById(List<Team> teams, int id) {
        for (Team team : teams) {
            if (team.getId() == id) {
                return team;
            }
        }
        return null;
    }
}
