package net.my4x.talk;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = { "net.my4x.talk.service" })
public class TalkConfig {

}
