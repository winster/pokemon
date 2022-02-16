package com.example.pokemon.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PokeApiResponseDto {

  private ResponseData data;

  @Getter
  @Setter
  public static class PokemonSpeciesFlavorTexts {
    @JsonProperty("flavor_text")
    private String text;
  }

  @Getter
  @Setter
  public static class PokemonHabitat {
    private String name;
  }

  @Getter
  @Setter
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
  @Setter
  public static class ResponseData {
    @JsonProperty("pokemon_v2_pokemonspecies")
    private List<PokemonSpecies> species;
  }
}
