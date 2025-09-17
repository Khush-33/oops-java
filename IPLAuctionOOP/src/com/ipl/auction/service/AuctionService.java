package com.ipl.auction.service;

import com.ipl.auction.model.Player;
import com.ipl.auction.model.PlayerStatus;
import com.ipl.auction.model.Team;

import java.util.List;
import java.util.Scanner;

/**
 * This class handles the core business logic of the auction.
 * It's an example of ABSTRACTION. The Main class doesn't need to know
 * the complex details of how a bidding cycle works; it just calls startAuction().
 */
public class AuctionService {
    private final List<Player> availablePlayers;
    private final List<Team> teams;
    private final Scanner scanner;

    public AuctionService(List<Player> availablePlayers, List<Team> teams) {
        this.availablePlayers = availablePlayers;
        this.teams = teams;
        this.scanner = new Scanner(System.in);
    }

    /**
     * The main public method to begin the auction process.
     */
    public void startAuction() {
        System.out.println("\n=================================");
        System.out.println("   THE IPL AUCTION BEGINS!");
        System.out.println("=================================");

        for (Player player : availablePlayers) {
            if (player.getStatus() == PlayerStatus.AVAILABLE) {
                conductBiddingCycle(player);
            }
        }

        System.out.println("\n=================================");
        System.out.println("    THE IPL AUCTION HAS ENDED!");
        System.out.println("=================================");
        scanner.close();
    }

    /**
     * Manages the bidding for a single player.
     * This complex logic is hidden (abstracted) from the Main class.
     */
    private void conductBiddingCycle(Player player) {
        System.out.println("\n---------------------------------------------------------");
        System.out.println("Bidding starts for: " + player.getName() + " (" + player.getRole() + ")");
        System.out.println("Base Price: ₹" + String.format("%,.0f", player.getBasePrice()));
        System.out.println("---------------------------------------------------------");

        double currentBid = 0;
        Team highestBidder = null;

        while (true) {
            System.out.println("\nEnter bid as 'TeamID Amount' (e.g., '1 5000000').");
            System.out.println("Type 'pass' to end bidding for this player.");
            System.out.print("Enter your bid > ");
            String input = scanner.nextLine();

            if ("pass".equalsIgnoreCase(input)) {
                break;
            }

            try {
                String[] parts = input.split(" ");
                int teamId = Integer.parseInt(parts[0]);
                double bidAmount = Double.parseDouble(parts[1]);

                Team biddingTeam = findTeamById(teamId);
                if (biddingTeam == null) {
                    System.out.println("Invalid Team ID. Please try again.");
                    continue;
                }

                // Validation logic
                if (bidAmount <= currentBid && currentBid > 0) {
                    System.out.println("Error: Bid must be higher than the current bid of ₹" + String.format("%,.0f", currentBid));
                    continue;
                }
                if (bidAmount < player.getBasePrice()) {
                     System.out.println("Error: Bid must be equal to or higher than the base price.");
                     continue;
                }
                if (bidAmount > biddingTeam.getBudget()) {
                    System.out.println("Error: " + biddingTeam.getName() + " does not have enough budget for this bid.");
                    continue;
                }

                // If bid is valid
                currentBid = bidAmount;
                highestBidder = biddingTeam;
                System.out.println("New highest bid: ₹" + String.format("%,.0f", currentBid) + " by " + highestBidder.getName());

            } catch (Exception e) {
                System.out.println("Invalid input format. Please try again.");
            }
        }

        // Conclude the bidding for the player
        if (highestBidder != null) {
            System.out.println("\n" + player.getName() + " is SOLD to " + highestBidder.getName() +
                               " for ₹" + String.format("%,.0f", currentBid) + "!");
            highestBidder.addPlayer(player, currentBid);
            player.setStatus(PlayerStatus.SOLD);
        } else {
            System.out.println("\n" + player.getName() + " remains UNSOLD.");
            player.setStatus(PlayerStatus.UNSOLD);
        }
    }

    private Team findTeamById(int id) {
        for (Team team : teams) {
            if (team.getId() == id) {
                return team;
            }
        }
        return null;
    }
}
