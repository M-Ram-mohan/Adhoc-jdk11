package pojos;

public class Dependency {
    public String vertexId;
    public String relation;
    public Dependency(String vertexId, String relation){
        this.relation = relation;
        this.vertexId = vertexId;
    }
    public String getVertexId(){
        return vertexId;
    }
    public String getRelation(){
        return  relation;
    }
}
