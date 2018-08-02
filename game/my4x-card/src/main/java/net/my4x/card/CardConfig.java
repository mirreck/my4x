package net.my4x.card;

import net.my4x.card.repository.RepositoryConfig;
import net.my4x.card.service.ServiceConfig;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(value = { RepositoryConfig.class, ServiceConfig.class })
public class CardConfig {

}
