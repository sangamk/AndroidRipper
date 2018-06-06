package it.unina.android.ripper.graphbuilder;

import it.unina.android.shared.ripper.model.state.ActivityDescription;
import it.unina.android.shared.ripper.model.task.Task;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Graph {
    public Set<Node> foundScreens = new HashSet<>();
    private HashMap<String, Edge> edges = new HashMap<>();

    public boolean addEdge(Edge edge){
        if (!edges.containsKey(edge.getId())){
            edges.put(edge.getId(), edge);
            return true;
        }
        return false;
    }

    public boolean addEdge(ActivityDescription source, Task transition, ActivityDescription target){
        if (source.getClassName().equals(target.getClassName())){
            return false;
        }

        Node sourceNode = new Node(source);
        Node targetNode = new Node(target);
        Edge transitionEdge = new Edge(sourceNode, transition, targetNode);
        if (addEdge(transitionEdge)){
            sourceNode.addNeigbour(transitionEdge);
            return true;
        }
        return false;
    }

    public HashMap<String, Edge> getEdges() {
        return edges;
    }
}
