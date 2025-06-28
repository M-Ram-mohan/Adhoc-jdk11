package pojos;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "stage")
public class Stage {
    @Id
    public String key;
    public String stage;
    public String comments;
    public String modelId;
    public int counter;
    public Stage(String key, String stage, String comments, String modelId, int counter){
        this.key= key;
        this.stage = stage;
        this.comments = comments;
        this.modelId = modelId;
        this.counter = counter;
    }
}

