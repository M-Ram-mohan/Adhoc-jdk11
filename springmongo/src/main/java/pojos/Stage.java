package pojos;

public class Stage {
    public String stage;
    public String comments;
    public String modelId;
    public Long timestamp;
    public Stage(String stage, String comments, String modelId, Long timestamp){
        this.stage = stage;
        this.comments = comments;
        this.modelId = modelId;
        this.timestamp = timestamp;
    }
}

