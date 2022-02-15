package com.example.pokemon.dto;

import lombok.Data;

@Data
public class PokeApiRequestDto {
  private String query;
  private Object variables;
}
