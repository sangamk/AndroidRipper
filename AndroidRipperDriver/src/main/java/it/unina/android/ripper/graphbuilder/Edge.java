package it.unina.android.ripper.graphbuilder;

import it.unina.android.shared.ripper.model.task.Task;

public class Edge {
    private Node source;
    private Node target;
    private Task transition;
    private String Id;

    public Edge(Node source, Task transition, Node target) {
        this.source = source;
        this.target = target;
        this.transition = transition;
        this.Id = source.getId() + target.getId();
    }

    public String getId(){
        return Id;
    }

    public Node getSource() {
        return source;
    }

    public Node getTarget() {
        return target;
    }

    public Task getTransition() {
        return transition;
    }
}
