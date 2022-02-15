package com.example.pokemon.controller;

import com.example.pokemon.dto.PokemonDto;
import com.example.pokemon.exception.NoResponseException;
import com.example.pokemon.service.PokemonService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@RestController
@RequestMapping("pokemon")
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


}
