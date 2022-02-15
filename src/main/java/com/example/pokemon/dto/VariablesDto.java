package com.example.pokemon.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.SneakyThrows;

@Data
public class VariablesDto {
  private String pokemonName;

  @SneakyThrows
  public String toString() {
    return new ObjectMapper().writeValueAsString(this);
  }
}
