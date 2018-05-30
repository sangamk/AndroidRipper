package it.unina.android.ripper.graphbuilder;

import it.unina.android.shared.ripper.model.state.ActivityDescription;

import java.util.ArrayList;
import java.util.List;

public class Node {
    private boolean start = false;
    private ActivityDescription activityDescription;
    private List<Edge> neighbours = new ArrayList<>();

    public Node(ActivityDescription activityDescription) {
        this.activityDescription = activityDescription;
    }

    public void setStart(boolean start) {
        this.start = start;
    }

    public void addNeigbour(Edge neighbour){
        neighbours.add(neighbour);
    }

    public String getId(){
        return activityDescription.getClassName();
    }

    public ActivityDescription getActivityDescription() {
        return activityDescription;
    }
}
