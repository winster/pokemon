package com.example.pokemon.service;

import com.example.pokemon.dto.PokemonDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
@Slf4j
public class PokemonService {

  public Mono<PokemonDto> getPokemonDetails(final String pokemonName) {
    return Mono.justOrEmpty(PokemonDto.builder().build());
  }

  public Mono<PokemonDto> getPokemonTranslatedDetails(final String pokemonName) {
    return Mono.justOrEmpty(PokemonDto.builder().build());
  }

}
