package pojos;

import java.util.ArrayList;
import java.util.List;

public class Node {
    public String vertexId;
    public String type;
    public List<Dependency> upstream;
    public List<Dependency> downstream;
    public Node(String vertexId, String type){
        this.vertexId = vertexId;
        this.type = type;
        this.upstream = new ArrayList<>();
        this.downstream = new ArrayList<>();
    }
}
