package com.example.pokemon.service;

import com.example.pokemon.config.AppConfigProperties;
import com.example.pokemon.dto.PokeApiResponseDto;
import com.example.pokemon.dto.PokemonDto;
import com.example.pokemon.dto.TranslationDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PokemonServiceTest {

  @InjectMocks
  PokemonService pokemonService;

  @Mock
  WebClient webClientMock;

  @Mock
  AppConfigProperties propertiesMock;

  @Mock
  private WebClient.RequestHeadersSpec requestHeadersMock;

  @Mock
  private WebClient.RequestHeadersUriSpec requestHeadersUriMock;

  @Mock
  private WebClient.RequestBodySpec requestBodyMock;

  @Mock
  private WebClient.RequestBodyUriSpec requestBodyUriMock;

  @Mock
  private WebClient.ResponseSpec responseMock;


  @Test
  void givenPokemonName_whenRequestPokeApiByName_thenReturnGraphqlResponse() {
    String pokemonName = "ditto";
    PokeApiResponseDto mockResponse = getMockPokeApiResponseDto();
    when(propertiesMock.getUrlPokeApi()).thenReturn("/pokemon/"+pokemonName);
    when(webClientMock.post()).thenReturn(requestBodyUriMock);
    when(requestBodyUriMock.uri(anyString())).thenReturn(requestBodyMock);
    when(requestBodyMock.bodyValue(any())).thenReturn(requestHeadersMock);
    when(requestHeadersMock.retrieve()).thenReturn(responseMock);
    when(responseMock.onStatus(any(Predicate.class), any(Function.class))).thenReturn(responseMock);
    when(responseMock.bodyToMono(PokeApiResponseDto.class)).thenReturn(Mono.just(mockResponse));

    Mono<PokeApiResponseDto> pokeApiResponseDtoMono = pokemonService.requestPokeApiByName(pokemonName);
    StepVerifier.create(pokeApiResponseDtoMono)
            .expectNextMatches(apiResponseDto -> apiResponseDto.getData().equals(mockResponse.getData()))
            .verifyComplete();
  }

  @Test
  void givenPokemonDto_whenRequestTranslationByName_thenReturnResponse() {
    PokemonDto pokemonDto = getMockPokemonDto();
    TranslationDto mockResponse = getMockTranslationDto();
    when(propertiesMock.getUrlShakespeare()).thenReturn("/pokemon/translated/"+pokemonDto.getName());
    when(propertiesMock.getUrlYoda()).thenReturn("/pokemon/translated/"+pokemonDto.getName());
    when(propertiesMock.getHabitat()).thenReturn("cave");
    when(webClientMock.post()).thenReturn(requestBodyUriMock);
    when(requestBodyUriMock.uri(anyString())).thenReturn(requestBodyMock);
    when(requestBodyMock.bodyValue(any())).thenReturn(requestHeadersMock);
    when(requestHeadersMock.retrieve()).thenReturn(responseMock);
    when(responseMock.onStatus(any(Predicate.class), any(Function.class))).thenReturn(responseMock);
    when(responseMock.bodyToMono(TranslationDto.class)).thenReturn(Mono.just(mockResponse));

    Mono<TranslationDto> translationDtoMono = pokemonService.requestTranslationApi(pokemonDto);
    StepVerifier.create(translationDtoMono)
            .expectNextMatches(apiResponseDto -> apiResponseDto.getContents().equals(mockResponse.getContents()))
            .verifyComplete();
  }

  @Test
  void givenPokemonName_whenGetPokemonDetailsByName_thenReturnPokemonDto() {
    PokemonDto pokemonDto = getMockPokemonDto();
    PokeApiResponseDto mockResponse = getMockPokeApiResponseDto();
    when(propertiesMock.getUrlPokeApi()).thenReturn("/pokemon/"+pokemonDto.getName());
    when(webClientMock.post()).thenReturn(requestBodyUriMock);
    when(requestBodyUriMock.uri(anyString())).thenReturn(requestBodyMock);
    when(requestBodyMock.bodyValue(any())).thenReturn(requestHeadersMock);
    when(requestHeadersMock.retrieve()).thenReturn(responseMock);
    when(responseMock.onStatus(any(Predicate.class), any(Function.class))).thenReturn(responseMock);
    when(responseMock.bodyToMono(PokeApiResponseDto.class)).thenReturn(Mono.just(mockResponse));

    Mono<PokemonDto> pokemonDtoMono = pokemonService.getPokemonDetailsByName(pokemonDto.getName());
    StepVerifier.create(pokemonDtoMono)
            .expectNextMatches(apiResponseDto -> apiResponseDto.equals(pokemonDto))
            .verifyComplete();
  }

  PokeApiResponseDto getMockPokeApiResponseDto() {
    PokeApiResponseDto.PokemonSpeciesFlavorTexts flavorTexts = new PokeApiResponseDto.PokemonSpeciesFlavorTexts();
    List<PokeApiResponseDto.PokemonSpeciesFlavorTexts> flavorTextsList = new ArrayList<>();
    PokeApiResponseDto.PokemonHabitat habitat = new PokeApiResponseDto.PokemonHabitat();
    PokeApiResponseDto.PokemonSpecies pokemonSpecies = new PokeApiResponseDto.PokemonSpecies();
    List<PokeApiResponseDto.PokemonSpecies> speciesList = new ArrayList<>();
    PokeApiResponseDto.ResponseData responseData = new PokeApiResponseDto.ResponseData();
    PokeApiResponseDto pokeApiResponseDto = new PokeApiResponseDto();
    flavorTexts.setText("It was created by\\na scientist after\\nyears of horrific\\fgene splicing and\\nDNA engineering\\nexperiments.");
    flavorTextsList.add(flavorTexts);
    habitat.setName("cave");
    pokemonSpecies.setName("ditto");
    pokemonSpecies.setHabitat(habitat);
    pokemonSpecies.setLegendary(false);
    pokemonSpecies.setFlavorTexts(flavorTextsList);
    speciesList.add(pokemonSpecies);
    responseData.setSpecies(speciesList);
    pokeApiResponseDto.setData(responseData);
    return pokeApiResponseDto;
  }

  PokemonDto getMockPokemonDto() {
    return PokemonDto.builder()
            .name("ditto")
            .description("It was created by\\na scientist after\\nyears of horrific\\fgene splicing and\\nDNA engineering\\nexperiments.")
            .isLegendary(false)
            .habitat("cave")
            .build();
  }

  TranslationDto getMockTranslationDto() {
    return TranslationDto.builder()
            .contents(TranslationDto.ContentsDto.builder()
                    .translated("test")
                    .build())
            .build();
  }
}
