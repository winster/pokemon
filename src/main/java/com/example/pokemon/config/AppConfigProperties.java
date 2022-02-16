package com.example.pokemon.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "app")
public class AppConfigProperties {
  private String urlPokeApi;
  private String urlShakespeare;
  private String urlYoda;
  private String habitat;
}
