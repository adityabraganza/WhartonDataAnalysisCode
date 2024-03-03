package NativeFunctions;

import Objects.game;

import java.util.Objects;

public class metrics{
    public static double[] getMetrics(String[] teamNames, game[] games){
        int gamesPlayedHome = 0;
        int gamesWonHome = 0;
        int gamesPlayedAway = 0;
        int gamesWonAway = 0;
        int gamesPlayedTogether = 0;
        int gamesHomeWonAgainstAway = 0;
        String homeTeam = teamNames[0];
        String awayTeam = teamNames[1];
        double differenceTotalHome = 0;
        double xGTotalHome = 0;
        double differenceTotalAway = 0;
        double xGTotalAway = 0;
        double goalsTotalHome = 0;
        double goalsTotalAway = 0;

        double[] returnArr = new double[11];

        for (game gm:games){
            double xGHome = 0;
            double goalsHome = 0;
            double xGAway = 0;
            double goalsAway = 0;

            if (Objects.equals(gm.homeTeam, homeTeam)){
                gamesPlayedHome++;
                if (Objects.equals(gm.winner, homeTeam)){
                    gamesWonHome++;
                }

                xGHome = gm.homeXG;
                goalsHome = gm.homeScore;
            }

            if (Objects.equals(gm.homeTeam, awayTeam)){
                gamesPlayedAway++;
                if (Objects.equals(gm.winner, awayTeam)){
                    gamesWonAway++;
                }

                xGAway = gm.awayXG;
                goalsAway = gm.awayScore;
            }

            if (gm.containsTeam(teamNames[0]) && gm.containsTeam(teamNames[1])){
                gamesPlayedTogether++;

                if(Objects.equals(gm.winner, teamNames[0])){
                    gamesHomeWonAgainstAway++;
                }
            }

            differenceTotalHome += Math.abs(goalsHome - xGHome);
            xGTotalHome += xGHome;
            differenceTotalAway += Math.abs(goalsAway - xGAway);
            xGTotalAway += xGAway;
            goalsTotalHome += goalsHome;
            goalsTotalAway += goalsAway;
        }

        if (gamesWonHome == 0){
            returnArr[0] = 0;
        } else{
            returnArr[0] = (double) gamesWonHome /gamesPlayedHome;
        }

        if (gamesWonAway == 0){
            returnArr[1] = 0;
        } else{
            returnArr[1] = (double) gamesWonAway /gamesPlayedAway;
        }

        if (gamesHomeWonAgainstAway == 0){
            returnArr[2] = 0;
        } else{
            returnArr[2] = (double) gamesHomeWonAgainstAway /gamesPlayedTogether;
        }

        double averageDifferenceHome = differenceTotalHome/gamesPlayedHome;
        double averageXGHome = xGTotalHome/gamesPlayedHome;

        returnArr[3] = averageXGHome - averageDifferenceHome;
        returnArr[4] = averageXGHome;
        returnArr[5] = averageXGHome + averageDifferenceHome;

        double averageDifferenceAway = differenceTotalAway/gamesPlayedAway;
        double averageXGAway = xGTotalAway/gamesPlayedAway;

        returnArr[6] = averageXGAway - averageDifferenceAway;
        returnArr[7] = averageXGAway;
        returnArr[8] = averageXGAway + averageDifferenceAway;

        returnArr[9] = goalsTotalHome/gamesPlayedHome;
        returnArr[10] = goalsTotalAway/gamesPlayedAway;

        return returnArr;
    }
}