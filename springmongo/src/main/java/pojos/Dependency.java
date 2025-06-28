package pojos;

import java.time.Instant;

public class Dependency {
    public String vertexId;
    public String relation;
    public Instant validFrom;
    public Instant validTo;
    public Dependency(String vertexId, String relation, Instant validFrom, Instant validTo){
        this.relation = relation;
        this.vertexId = vertexId;
        this.validFrom = validFrom;
        this.validTo = validTo;
    }
    public String getVertexId(){
        return vertexId;
    }
    public String getRelation(){
        return  relation;
    }
    public Instant getValidFrom() {
        return validFrom;
    }
    public Instant getValidTo() {
        return validTo;
    }
}
