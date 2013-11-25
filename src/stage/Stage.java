package stage;

import java.util.ArrayList;
import java.util.List;
import team.Team;
import view.canvas.GridMouseListener;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import gameObject.GameUnit;
import grid.Coordinate;
import grid.Grid;


/**
 * Stage is responsible for managing how turns are distributed and progressing
 * the game when it is won. The turns progress when the player indicates they are
 * done and when the AI deactivates all of their units.
 * 
 * @author Andy Bradshaw
 * @author carlosreyes
 * @author Leevi
 * 
 */
@JsonAutoDetect
public class Stage implements GridMouseListener {

    @JsonProperty
    private Grid myGrid;
    private String myName;
    private String preText;
    private String postText;
    private List<Team> myTeamList;
    private Team myWinningTeam;
    
    // only for use by deserializer
    public Stage () {
        myTeamList = new ArrayList<Team>();
    }

    public Stage (int x, int y, int tileID, String name) {
        myGrid = new Grid(x, y, tileID);
        myName = name;
        myTeamList = new ArrayList<Team>();
    }

    /*
     * Returns true if unit was added to team, false if teamID was invalid
     * Note this logic works best if editor has a "team editor" tab that
     * allows users to make teams and assign units to those teams.
     */

    public boolean addUnitToTeam (int teamID, GameUnit gu) {
        if (teamID < myTeamList.size()) {
            myTeamList.get(teamID).addGameUnit(gu);
            gu.setAffiliation(myTeamList.get(teamID).getName());
            return true;
        }
        return false;
    }

    public void addTeam (String teamName) {
        myTeamList.add(new Team(teamName,true));
    }

    public Team getTeam (int teamID) {
        if (teamID < myTeamList.size()) { return myTeamList.get(teamID); }
        return null;
    }

    @JsonIgnore
    public int getNumberOfTeams(){
        return myTeamList.size();
    }
    
    public Grid getGrid () {
        return myGrid;
    }

    public void setName (String name) {
        myName = name;
    }

    public String getName () {
        return myName;
    }

    @JsonIgnore
    public List<String> getTeamNames () {
        List<String> ret = new ArrayList<String>();
        for (Team t : myTeamList) {
            ret.add(t.getName());
        }

        return ret;
    }

    public List<GameUnit> getTeamUnits (int ID) {
        if (ID < myTeamList.size()) { return myTeamList.get(ID).getGameUnits(); }

        return null;
    }

    public void setPreStory (String pre) {
        preText = pre;
    }

    public void setPostStory (String post) {
        postText = post;
    }

    public String getPreStory () {
        return preText;
    }

    public String getPostStory () {
        return postText;
    }

    @Override
    public void gridClicked (Coordinate c) {
        System.out.println(c);
    }

    public boolean conditionsMet () {
        boolean conditionsMet = false;

        for (Team t : myTeamList) {
            conditionsMet = conditionsMet || t.hasWon(this);
            if(t.hasWon(this)){
                myWinningTeam = t;
                // teams with lower IDs have a slight disadvantage here but that's offset by the fact that their turn comes up later.
            }
        }

        return false;
    }
    
    public Team getWinningTeam(){
        return myWinningTeam;
    }
}
