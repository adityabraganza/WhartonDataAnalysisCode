import NativeFunctions.data;
import NativeFunctions.metrics;
import Objects.game;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import NativeFunctions.optimization;

public class Main {
    public static void main(String[] args) {
        getProbability();
    }
    public static void bestMetric(){
        game[] games = data.getGames();
        int numberOfNotDraw = 0;
        int correctGuesses = 0;
        int home = 0;
        int away = 0;

        for (game gm : games) {
            double sum = getSum(metrics.getMetrics(new String[]{gm.homeTeam, gm.awayTeam}, games), new int[]{1, 2, 3, 2}, new int[]{3, 3, 3, 2});
            if(sum > 0){
                System.out.println(gm.homeTeam);
                home++;
            } else{
                System.out.println(gm.awayTeam);
                away++;
            }
        }

        System.out.println(numberOfNotDraw);
        System.out.println(home);
        System.out.println(away);
        System.out.println((double) correctGuesses/numberOfNotDraw);
    }
    public static void getProbability(){
        int[] weightsEquation = {3, 3, 3, 2};
        int[] weightsMetric = {1, 2, 3, 2};

        game[] games = data.getGames();
        for (game gm: games){
            gm.init();
        }
        String[] team1 = {
                "DOV",
                "FOR",
                "DOV",
                "DOV",
                "OAK",
                "OAK"
        };
        String[] team2 = {
                "OAK",
                "AUG",
                "FOR",
                "AUG",
                "FOR",
                "AUG"
        };

        for (int i = 0; i < team1.length; i++){
            double sum = getSum(metrics.getMetrics(new String[]{team1[i], team2[i]}, games), weightsMetric, weightsEquation);
            System.out.println(team1[i] + " " + ((sum+4)/8));
        }
    }
    public static void getWins(){
        game[] games = data.getGames();
        for (game gm: games){
            gm.init();
        }

        int[] weightsEquation = {3, 3, 3, 2};
        int[] weightsMetric = {1, 2, 3, 2};

        String[] homeTeams = {"ANC",
                "DOV",
                "TAC",
                "DOV",
                "ANC",
                "LEX",
                "FAR",
                "SPR",
                "TUC",
                "SPR",
                "FAR",
                "TOL",
                "SFS",
                "LRO",
                "WIC",
                "LRO",
                "SFS",
                "DES",
                "PRO",
                "OAK",
                "ALB",
                "OAK",
                "PRO",
                "CHM",
                "LAR",
                "SAS",
                "MOB",
                "SAS",
                "LAR",
                "FOR",
                "JAC",
                "REN",
                "MAN",
                "REN",
                "JAN",
                "BAK",
                "AUG",
                "BOI",
                "SJU",
                "BOI",
                "AUG",
                "EUG"};
        String[] awayTeams = {"LEX",
                "TAC",
                "ANC",
                "LEX",
                "DOV",
                "TAC",
                "TOL",
                "TUC",
                "FAR",
                "TOL",
                "SPR",
                "TUC",
                "DES",
                "WIC",
                "SFS",
                "DES",
                "LRO",
                "WIC",
                "CHM",
                "ALB",
                "PRO",
                "CHM",
                "OAK",
                "ALB",
                "FOR",
                "MOB",
                "LAR",
                "FOR",
                "SAS",
                "MOB",
                "BAK",
                "MAN",
                "JAC",
                "BAK",
                "REN",
                "MAN",
                "EUG",
                "SJU",
                "AUG",
                "EUG",
                "BOI",
                "SJU"};

        for (int i = 0; i < homeTeams.length; i++){
            double sum = getSum(metrics.getMetrics(new String[]{homeTeams[i], awayTeams[i]}, games), weightsMetric, weightsEquation);
            if(sum > 0){
                System.out.println(homeTeams[i]);
            } else{
                System.out.println(awayTeams[i]);
            }
        }
    }
    public static void weights(){
        List<int[]> combinationsMetric = optimization.getWeight(new int[]{0, 0, 0, 0}, 0, 3);
        List<int[]> combinationsEquation = optimization.getWeight(new int[]{0, 0, 0, 0}, 0, 3);

        int[] bestWeightEquation = {0, 0, 0, 0};
        int[] bestWeightMetric = {0, 0, 0, 0};
        double bestAccuracy = 0;
        int homeBest = 0;
        int awayBest = 0;

        game[] games = data.getGames();
        for (game gm : games) {
            gm.init();
        }

        for (int[] combinationMetric : combinationsMetric) {
            for (int[] ints : combinationsEquation) {
                int numberOfNotDraw = 0;
                int correctGuesses = 0;
                int home = 0;
                int away = 0;

                for (game gm : games) {
                    if (!Objects.equals(gm.winner, "draw")) {
                        numberOfNotDraw++;

                        String[] teamNames = new String[2];
                        teamNames[0] = gm.homeTeam;
                        teamNames[1] = gm.awayTeam;

                        double[] metricsGM = metrics.getMetrics(teamNames, games);

                        double sum = getSum(metricsGM, combinationMetric, ints);

                        if (sum > 0 && Objects.equals(gm.winner, teamNames[0])) {
                            correctGuesses++;
                            home++;
                        } else if (sum < 0 && Objects.equals(gm.winner, teamNames[1])) {
                            correctGuesses++;
                            away++;
                        }
                    }
                }

                if ((double) correctGuesses / numberOfNotDraw > bestAccuracy) {
                    bestWeightEquation = ints;
                    bestWeightMetric = combinationMetric;
                    bestAccuracy = (double) correctGuesses / numberOfNotDraw;
                    homeBest = home;
                    awayBest = away;
                }
            }

            System.out.println(Arrays.toString(bestWeightEquation));
            System.out.println(Arrays.toString(bestWeightMetric));
            System.out.println(bestAccuracy);
            System.out.println(homeBest);
            System.out.println(awayBest);
        }
        System.out.println(Arrays.toString(bestWeightEquation));
        System.out.println(Arrays.toString(bestWeightMetric));
        System.out.println(bestAccuracy);
        System.out.println(homeBest);
        System.out.println(awayBest);
    }

    private static double getSum(double[] metricsGM, int[] weightsMetric, int[] weightsEquation) {
        double xGDifferenceLow = metricsGM[3] - metricsGM[6];
        double xGDifferenceMedium = metricsGM[4] - metricsGM[7];
        double xGDifferenceHigh = metricsGM[5] - metricsGM[8];

        double xGSum = xGDifferenceLow + xGDifferenceMedium + xGDifferenceHigh;
        double totalGoalDifference = metricsGM[9] - metricsGM[10];
        double winRateHome = metricsGM[0] - metricsGM[1];
        double winRateHomeSpecific = metricsGM[2];

        xGSum *= weightsMetric[0];
        totalGoalDifference *= weightsMetric[1];
        winRateHome *= weightsMetric[2];
        winRateHomeSpecific *= weightsMetric[3];

        xGSum = (1 / (-weightsEquation[0] * xGSum)) + 1;
        totalGoalDifference = (1 / (-weightsEquation[1] * totalGoalDifference)) + 1;
        winRateHome = (1 / (-weightsEquation[2] * winRateHome)) + 1;
        winRateHomeSpecific = (1 / (-weightsEquation[3] * winRateHomeSpecific)) + 1;

        if (xGSum > 1){
            xGSum = 1;
        } else if(xGSum < -1){
            xGSum = -1;
        }

        if (totalGoalDifference > 1){
            totalGoalDifference = 1;
        } else if(totalGoalDifference < -1){
            totalGoalDifference = -1;
        }

        if (winRateHome > 1){
            winRateHome = 1;
        } else if(winRateHome < -1){
            winRateHome = -1;
        }

        if (winRateHomeSpecific > 1){
            winRateHomeSpecific = 1;
        } else if(winRateHomeSpecific < -1){
            winRateHomeSpecific = -1;
        }

        double sum = xGSum + totalGoalDifference + winRateHome + winRateHomeSpecific;
        System.out.println((sum + 4) / 8);
        if (sum < 0){
            xGDifferenceLow = metricsGM[6] - metricsGM[4];
            xGDifferenceMedium = metricsGM[7] - metricsGM[4];
            xGDifferenceHigh = metricsGM[8] - metricsGM[5];

            xGSum = xGDifferenceLow + xGDifferenceMedium + xGDifferenceHigh;
            totalGoalDifference = metricsGM[10] - metricsGM[9];
            winRateHome = metricsGM[1] - metricsGM[0];
            winRateHomeSpecific = metricsGM[2];

            xGSum *= weightsMetric[0];
            totalGoalDifference *= weightsMetric[1];
            winRateHome *= weightsMetric[2];
            winRateHomeSpecific *= weightsMetric[3];

            xGSum = (1 / (-weightsEquation[0] * xGSum)) + 1;
            totalGoalDifference = (1 / (-weightsEquation[1] * totalGoalDifference)) + 1;
            winRateHome = (1 / (-weightsEquation[2] * winRateHome)) + 1;
            winRateHomeSpecific = (1 / (-weightsEquation[3] * winRateHomeSpecific)) + 1;

            if (xGSum > 1){
                xGSum = 1;
            } else if(xGSum < -1){
                xGSum = -1;
            }

            if (totalGoalDifference > 1){
                totalGoalDifference = 1;
            } else if(totalGoalDifference < -1){
                totalGoalDifference = -1;
            }

            if (winRateHome > 1){
                winRateHome = 1;
            } else if(winRateHome < -1){
                winRateHome = -1;
            }

            if (winRateHomeSpecific > 1){
                winRateHomeSpecific = 1;
            } else if(winRateHomeSpecific < -1){
                winRateHomeSpecific = -1;
            }

            sum = xGSum + totalGoalDifference + winRateHome + winRateHomeSpecific;
        }

        return sum;
    }
}