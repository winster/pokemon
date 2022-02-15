package com.example.pokemon.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Data
public class PokeApiResponseDto {

  private ResponseData data;

  @Getter
  public static class PokemonSpeciesFlavorTexts {
    @JsonProperty("flavor_text")
    private String text;
  }

  @Getter
  public static class PokemonHabitat {
    private String name;
  }

  @Getter
  public static class PokemonSpecies {
    private String name;

    @JsonProperty("is_legendary")
    private boolean legendary;

    @JsonProperty("pokemon_v2_pokemonspeciesflavortexts")
    private List<PokemonSpeciesFlavorTexts> flavorTexts;

    @JsonProperty("pokemon_v2_pokemonhabitat")
    private PokemonHabitat habitat;
  }

  @Getter
  public static class ResponseData {
    @JsonProperty("pokemon_v2_pokemonspecies")
    private List<PokemonSpecies> species;
  }
}
