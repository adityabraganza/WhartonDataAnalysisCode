package Objects;

import java.util.Objects;

public class game {
    public int game_id;
    public String homeTeam;
    public String awayTeam;
    public int homeScore;
    public int awayScore;
    public double homeXG;
    public double awayXG;
    public String winner;
    public double homeXGConversion;
    public double awayXGConversion;

    public game(int game_id, String homeTeam, String awayTeam, int homeScore, int awayScore, double homeXG, double awayXG){
        this.game_id = game_id;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.homeScore = homeScore;
        this.awayScore = awayScore;
        this.homeXG = homeXG;
        this.awayXG = awayXG;
    }

    public void init(){
        if (homeScore == awayScore){
            winner = "draw";
        } else if (homeScore > awayScore){
            winner = homeTeam;
        } else{
            winner = awayTeam;
        }

        homeXGConversion = (homeScore / homeXG) * 100;
        awayXGConversion = (awayScore / awayXG) * 100;
    }

    public boolean containsTeam(String teamName){
        return ((Objects.equals(homeTeam, teamName)) || (Objects.equals(awayTeam, teamName)));
    }

    @Override
    public String toString() {
        return "game{" +
                "game_id=" + game_id +
                ", homeTeam='" + homeTeam + '\'' +
                ", awayTeam='" + awayTeam + '\'' +
                ", homeScore=" + homeScore +
                ", awayScore=" + awayScore +
                ", homeXG=" + homeXG +
                ", awayXG=" + awayXG +
                ", winner='" + winner + '\'' +
                ", homeXGConversion=" + homeXGConversion +
                ", awayXGConversion=" + awayXGConversion +
                '}';
    }
}
