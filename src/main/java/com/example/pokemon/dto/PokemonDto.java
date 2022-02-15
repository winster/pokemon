package com.example.pokemon.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PokemonDto {
  private String name;
  private String description;
  private String habitat;
  private boolean isLegendary;
}
