package stage;

import gameObject.GameObject;
import gameObject.GameUnit;
import grid.Grid;
import grid.GridConstants;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import team.Team;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * Stage is responsible for managing how turns are distributed and progressing
 * the game when it is won. The turns progress when the player indicates they
 * are done and when the AI deactivates all of their units.
 * 
 * @author Andy Bradshaw
 * @author carlosreyes
 * @author Leevi
 * 
 */
@JsonAutoDetect
public class Stage {

    @JsonProperty
    private Grid myGrid;
    private String myName;
    private String preText;
    private String postText;
    private List<Team> myTeams;
    private Team myWinningTeam;
    
    private int myPhaseCount;

    // only for use by deserializer
    public Stage () {
    }

    public Stage (int x, int y, int tileID, String name) {
        myGrid = new Grid(x, y, tileID);
        myName = name;
        myTeams = new ArrayList<Team>();
        preText = "Once upon a time...";
        postText = "Thus the journey ends!";
    }

    /*
     * Returns true if unit was added to team, false if teamID was invalid Note
     * this logic works best if editor has a "team editor" tab that allows users
     * to make teams and assign units to those teams.
     */

    public boolean addUnitToTeam (int teamID, GameUnit gu) {
        if (teamID < myTeams.size()) {
            gu.setAffiliation(myTeams.get(teamID).getName());
            return true;
        }
        return false;
    }

    public Team getTeam (int teamID) {
        if (teamID < myTeams.size()) { return myTeams.get(teamID); }
        return null;
    }

    // for use by editor
    public List<Team> getTeams () {
        return myTeams;
    }

    public void setTeams (List<Team> teams) {
        myTeams = teams;
    }

    @JsonIgnore
    public void setTeamName (int teamID, String newName) {
        if (teamID < myTeams.size()) {
            for (GameUnit gu : getTeamUnits(myTeams.get(teamID).getName())) {
                gu.setAffiliation(newName);
            }
            myTeams.get(teamID).setName(newName);
        }
    }

    @JsonIgnore
    public int getNumberOfTeams () {
        return myTeams.size();
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
        for (Team t : myTeams) {
            ret.add(t.getName());
        }

        return ret;
    }

    public List<GameUnit> getTeamUnits (String teamName) {
        GameUnit[][] units = myGrid.getGameUnits();
        List<GameUnit> ret = new ArrayList<GameUnit>();

        for (int i = 0; i < units.length; i++) {
            for (GameUnit gu : units[i]) {
                if (gu != null && teamName.equals(gu.getAffiliation())) {
                    ret.add(gu);
                }
            }
        }
        return ret;
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

    public boolean conditionsMet () {
        boolean conditionsMet = false;

        for (Team t : myTeams) {
            conditionsMet = conditionsMet || t.hasWon(this);
            if (t.hasWon(this)) {
                myWinningTeam = t;
                // teams with lower IDs have a slight disadvantage here but
                // that's offset by the fact that their turn comes up later.
            }
        }

        return conditionsMet;
    }

    public Team getWinningTeam () {
        return myWinningTeam;
    }

    public int getPhaseCount () {
        return myPhaseCount;
    }

    public void setPhaseCount (int phaseCount) {
        myPhaseCount = phaseCount;
    }

}
