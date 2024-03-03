import NativeFunctions.data;
import NativeFunctions.metrics;
import Objects.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import NativeFunctions.optimization;

public class Main {
    public static void main(String[] args) {
        List<int[]> combinationsMetric = optimization.getWeight(new int[]{0, 0, 0, 0}, 3);
        List<int[]> combinationsEquation = optimization.getWeight(new int[]{0, 0, 0, 0, 0}, 3);

        int[] bestWeightEquation = {0, 0, 0, 0, 0};
        int[] bestWeightMetric = {0, 0, 0, 0};
        double bestAccuracy = 0;

        game[] games = data.getGames();
        for (game gm : games) {
            gm.init();
        }

        for (int[] combinationMetric : combinationsMetric) {
            for (int[] ints : combinationsEquation) {
                int numberOfNotDraw = 0;
                int correctGuesses = 0;

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
                        } else if (sum < 0 && Objects.equals(gm.winner, teamNames[1])) {
                            correctGuesses++;
                        }
                    }
                }

                if ((double) correctGuesses / numberOfNotDraw > bestAccuracy) {
                    bestWeightEquation = ints;
                    bestWeightMetric = combinationMetric;
                    bestAccuracy = (double) correctGuesses / numberOfNotDraw;
                }
            }
            System.out.println(Arrays.toString(combinationMetric));
            System.out.println(bestAccuracy);
        }
        System.out.println(Arrays.toString(bestWeightEquation));
        System.out.println(Arrays.toString(bestWeightMetric));
        System.out.println(bestAccuracy);
    }

    private static double getSum(double[] metricsGM, int[] weightsMetric, int[] weightsEquation) {
        double xGDifferenceLow = metricsGM[3] - metricsGM[6];
        double xGDifferenceMedium = metricsGM[4] - metricsGM[7];
        double xGDifferenceHigh = metricsGM[5] - metricsGM[8];

        double xGSum = xGDifferenceLow + xGDifferenceMedium + xGDifferenceHigh;
        double totalGoalDifference = metricsGM[9] - metricsGM[10];
        double winRateHome = metricsGM[0] - metricsGM[1];
        double winRateHomeSpecific = metricsGM[2];

        if (xGSum > 0) {
            xGSum *= weightsMetric[0];
            xGSum = (1 / ((double) 1 / ((-weightsEquation[0] * xGSum) - weightsEquation[4]))) + 1;
        }

        if (totalGoalDifference > 0){
            totalGoalDifference *= weightsMetric[1];
            totalGoalDifference = (1 / ((double) 1 / ((-weightsEquation[1] * totalGoalDifference) - weightsEquation[4]))) + 1;
        }

        if (winRateHome > 0){
            winRateHome *= weightsMetric[2];
            winRateHome = (1 / ((double) 1 / ((-weightsEquation[2] * winRateHome) - weightsEquation[4]))) + 1;
        }

        if (winRateHomeSpecific > 0){
            winRateHomeSpecific *= weightsMetric[3];
            winRateHomeSpecific = (1 / ((double) 1 / ((-weightsEquation[3] * winRateHomeSpecific) - weightsEquation[4]))) + 1;
        }

        return xGSum + totalGoalDifference + winRateHome + winRateHomeSpecific;
    }
}
