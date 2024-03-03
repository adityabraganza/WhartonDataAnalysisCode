package NativeFunctions;

import Objects.game;
import Data.NSL_regular_season_data_2;

public class data {
    public static game[] getGames(){
        String[] dataGiven = NSL_regular_season_data_2.dataString;
        game[] returnArr = new game[dataGiven.length];

        if (dataGiven[12] == null){

        }

            //12 & 13, 0.76

        for (int i = 0; i < dataGiven.length; i++){
            String[] values = dataGiven[i].split(",");

            returnArr[i] = new game(
                    Integer.parseInt(values[0].split("_")[2]),
                    values[1],
                    values[2],
                    Integer.parseInt(values[3]),
                    Integer.parseInt(values[4]),
                    Double.parseDouble(values[5]) - (0.76 * Double.parseDouble(values[12])),
                    Double.parseDouble(values[6]) - (0.76 * Double.parseDouble(values[13]))
            );
        }

        return returnArr;
    }
}
