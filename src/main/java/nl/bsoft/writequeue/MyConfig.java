package nl.bsoft.writequeue;


import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@Configuration
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
@ComponentScan("nl.bsoft")
public class MyConfig {
}
