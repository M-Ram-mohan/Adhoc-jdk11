package pojos;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "graph")
public class Node {
    @Id
    public String vertexId;
    public String bridgeNode;
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
