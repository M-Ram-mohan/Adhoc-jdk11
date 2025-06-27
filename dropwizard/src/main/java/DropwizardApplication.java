import config.DropwizardConfig;
import config.SpringConfig;
import io.dropwizard.Application;
import io.dropwizard.setup.Environment;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import rest.DropwizardResource;

public class DropwizardApplication extends Application<DropwizardConfig> {
    public static void main(String[] args) throws Exception {
        new DropwizardApplication().run(args);
    }

    @Override
    public void run(DropwizardConfig config, Environment environment) {
        System.out.println("Hello, Server started" + config.getMessage());
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.getBeanFactory().registerSingleton("dropwizardConfig", config);
        context.register(SpringConfig.class);
        context.refresh();
        environment.jersey().register(new DropwizardResource());
    }

}
/*
TODO :
1. try sockets, servlets etc in java

Integrating dropwizard config into spring

@Override
    public void run(MyAppConfiguration configuration, Environment environment) {
        context = new AnnotationConfigApplicationContext();

        // Register your Spring @Configuration class
        context.register(MySpringConfig.class);

        // Register the Dropwizard configuration as a Spring bean
        context.getBeanFactory().registerSingleton("myAppConfiguration", configuration);

        context.refresh();

        // Register resources and health checks
        environment.jersey().register(context.getBean(MyResource.class));

Spring configuration

@Configuration
@ComponentScan(basePackages = "com.example.myapp")
public class MySpringConfig {

    @Autowired
    private MyAppConfiguration config;

    @Bean
    public MyService myService() {
        return new MyServiceImpl(config.getSomeProperty());
    }
}

clean way to integrate stuff
@Override
    public void run(MyAppConfiguration configuration, Environment environment) {
        // 1. Initialize Spring context
        context = new AnnotationConfigApplicationContext();
        context.register(MySpringConfig.class);
        context.refresh();

        // 2. Inject Dropwizard environment/config into Spring if needed
        context.getBeanFactory().registerSingleton("dwEnvironment", environment);
        context.getBeanFactory().registerSingleton("dwConfiguration", configuration);

        // 3. Register Spring-managed resources with Dropwizard
        MyResource myResource = context.getBean(MyResource.class);
        environment.jersey().register(myResource);

        // 4. Optionally: register Spring health checks
        MyHealthCheck myHealthCheck = context.getBean(MyHealthCheck.class);
        environment.healthChecks().register("myHealth", myHealthCheck);
    }

Points to remember :
- You don't have to pass the spring context around because you can get the objects you
want directly by autowiring
- You can use spring to manage lifecycle of the beans. So, think about the components
you are going to create as beans and then think about the lifecycle hooks those
components would need.
- 


 */
