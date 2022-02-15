package com.example.pokemon.controller;

import com.example.pokemon.dto.PokemonDto;
import com.example.pokemon.exception.NoResponseException;
import com.example.pokemon.service.PokemonService;
import io.micrometer.core.annotation.Timed;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@RestController
@RequestMapping("pokemon")
@Timed
public class PokeController {

  private final PokemonService pokemonService;

  @GetMapping("/{name}")
  public Mono<PokemonDto> getPokemonDetails(@PathVariable final String name) {
    return pokemonService.getPokemonDetails(name);
  }

  @GetMapping("/translated/{name}")
  public Mono<PokemonDto> getPokemonTranslatedDetails(@PathVariable final String name) {
    return pokemonService.getPokemonTranslatedDetails(name);
  }

  @ExceptionHandler
  public ResponseEntity<String> handle(NoResponseException exception) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
  }

  @ExceptionHandler
  public ResponseEntity<String> handle(WebClientResponseException exception) {
    return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(exception.getMessage());
  }

}
