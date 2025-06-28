package config;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;

public class DropwizardConfig extends Configuration {
    @JsonProperty
    private String message;

    public String getMessage(){
        return message;
    }
}
