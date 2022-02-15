package com.example.pokemon.service;

import com.example.pokemon.dto.PokemonDto;
import com.example.pokemon.dto.PokemonGraphqlRequestBodyDto;
import com.example.pokemon.dto.VariablesDto;
import com.example.pokemon.util.FileUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
@Slf4j
public class PokemonService {

  public Mono<String> getPokemonDetails(final String pokemonName) {
    return requestPokeApi(pokemonName);
  }

  public Mono<PokemonDto> getPokemonTranslatedDetails(final String pokemonName) {
    return Mono.justOrEmpty(PokemonDto.builder().build());
  }

  /**
   * Invoke PokeApi Graphql Endpoint
   * @param pokemonName
   * @return
   */
  private Mono<String> requestPokeApi(final String pokemonName) {
    log.info("inside requestPokeApi");
    WebClient webClient = WebClient.builder().build();
    PokemonGraphqlRequestBodyDto requestBodyDto = new PokemonGraphqlRequestBodyDto();
    final String query = FileUtil.getSchemaFromFileName("getPokemonDetails");
    VariablesDto variablesDto = new VariablesDto();
    variablesDto.setPokemonName(pokemonName);
    requestBodyDto.setQuery(query);
    requestBodyDto.setVariables(variablesDto);
    log.info("graphQLRequestBody {}", requestBodyDto);
    return webClient.post()
            .uri("https://beta.pokeapi.co/graphql/v1beta")
            .bodyValue(requestBodyDto)
            .retrieve()
            .onStatus(HttpStatus.NOT_FOUND::equals, ClientResponse::createException)
            .bodyToMono(String.class);
  }

}
