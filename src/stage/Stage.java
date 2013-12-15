package stage;

import gameObject.GameUnit;
import grid.Grid;
import grid.GridConstants;
import java.util.ArrayList;
import java.util.List;
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
    @JsonProperty
    private Team myWinningTeam;
    private int myPhaseCount;

    public Stage () {
    }

    /**
     * Constructor for the WorldManager to use when creating a new stage
     * 
     * @param x Grid width in tiles
     * @param y Grid height in tiles
     * @param tileID tileID of the tile to load in
     * @param name name of the stage
     */
    public Stage (int x, int y, String name) {
        myGrid = new Grid(x, y);
        myName = name;
        myTeams = new ArrayList<Team>();
        preText = "Once upon a time...";
        postText = "Thus the journey ends!";
    }

    /**
     * Gets the team associated with the teamID
     * 
     * @param teamID
     * @return Team associated with the teamID
     */
    @JsonIgnore
    public Team getTeam (int teamID) {
        if (teamID < myTeams.size()) { return myTeams.get(teamID); }
        return null;
    }

    /**
     * Helper function for when the user edits a team name in the editor
     * 
     * @param teamID ID of the team that changed names
     * @param newName The new name of the team
     */
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
    public List<GameUnit> getTeamUnits (String teamName) {
        GameUnit[][] units = (GameUnit[][]) myGrid.getArray(GridConstants.GAMEUNIT);
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

    @JsonIgnore
    public Grid getGrid () {
        return myGrid;
    }

    /**
     * Checks to see if the any of the team's win conditions have been met
     * If so, it sets myWinningTeam to that team. Teams with higher IDs have
     * a built in advantage here to offset the fact that their turn comes up later.
     * 
     */
    @JsonIgnore
    public boolean conditionsMet () {
        boolean conditionsMet = false;

        for (Team t : myTeams) {
            conditionsMet = conditionsMet || t.hasWon(this);
            if (t.hasWon(this)) {
                myWinningTeam = t;
            }
        }

        return conditionsMet;
    }

    @JsonIgnore
    public int getNumberOfTeams () {
        return myTeams.size();
    }

    @JsonIgnore
    public Team getWinningTeam () {
        return myWinningTeam;
    }

    public void setName (String name) {
        myName = name;
    }

    public String getName () {
        return myName;
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

    public int getPhaseCount () {
        return myPhaseCount;
    }

    public void setPhaseCount (int phaseCount) {
        myPhaseCount = phaseCount;
    }

    public List<Team> getTeams () {
        return myTeams;
    }

    public void setTeams (List<Team> teams) {
        myTeams = teams;
    }
}
