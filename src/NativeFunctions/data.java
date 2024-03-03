package NativeFunctions;

import Objects.game;
import Data.NSL_regular_season_data_2;

public class data {
    public static game[] getGames(){
        String[] dataGiven = NSL_regular_season_data_2.dataString;
        game[] returnArr = new game[dataGiven.length];

        for (int i = 0; i < dataGiven.length; i++){
            String[] values = dataGiven[i].split(",");
            returnArr[i] = new game(
                    Integer.parseInt(values[0].split("_")[2]),
                    values[1],
                    values[2],
                    Integer.parseInt(values[3]),
                    Integer.parseInt(values[4]),
                    Double.parseDouble(values[5]),
                    Double.parseDouble(values[6])
            );
        }

        return returnArr;
    }
}
