package com.example.pokemon.dto;

import lombok.Data;

@Data
public class PokemonGraphqlRequestBodyDto {
  private String query;
  private Object variables;
}
