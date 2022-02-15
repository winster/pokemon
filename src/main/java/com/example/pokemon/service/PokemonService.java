package com.example.pokemon.service;

import com.example.pokemon.dto.PokeApiResponseDto;
import com.example.pokemon.dto.PokemonDto;
import com.example.pokemon.dto.PokeApiRequestDto;
import com.example.pokemon.dto.VariablesDto;
import com.example.pokemon.exception.NoResponseException;
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

  public Mono<PokemonDto> getPokemonDetails(final String pokemonName) {
    return requestPokeApi(pokemonName)
            .flatMap(this::mapPokeApiResponse);
  }

  public Mono<PokemonDto> getPokemonTranslatedDetails(final String pokemonName) {
    return Mono.justOrEmpty(PokemonDto.builder().build());
  }

  /**
   * Invoke PokeApi Graphql Endpoint
   * @param pokemonName
   * @return
   */
  private Mono<PokeApiResponseDto> requestPokeApi(final String pokemonName) {
    log.info("inside requestPokeApi");
    WebClient webClient = WebClient.builder().build();
    PokeApiRequestDto requestBodyDto = new PokeApiRequestDto();
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
            .bodyToMono(PokeApiResponseDto.class);
  }

  /**
   * Map PokeApi graphql response to PokemonDto
   * @param responseDto
   * @return
   */
  private Mono<PokemonDto> mapPokeApiResponse(PokeApiResponseDto responseDto) {
    log.info("inside convertGraphqlResponse");
    if (responseDto.getData() == null || responseDto.getData().getSpecies() == null ||
            responseDto.getData().getSpecies().size() == 0) {
      return Mono.error(new NoResponseException("No species found"));
    }
    PokeApiResponseDto.PokemonSpecies pokemonSpecies = responseDto.getData().getSpecies().get(0);
    if (pokemonSpecies.getHabitat() == null ||
            pokemonSpecies.getFlavorTexts() == null || pokemonSpecies.getFlavorTexts().size() == 0) {
      return Mono.error(new NoResponseException("Neither habitat nor flavor texts found"));
    }
    return Mono.just(PokemonDto.builder()
            .name(pokemonSpecies.getName())
            .isLegendary(pokemonSpecies.isLegendary())
            .habitat(pokemonSpecies.getHabitat().getName())
            .description(pokemonSpecies.getFlavorTexts().get(0).getText())
            .build());
  }

}
