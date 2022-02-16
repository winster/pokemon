package com.example.pokemon.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class TranslationDto {
  private SuccessDto success;
  private ContentsDto contents;

  @Getter
  public static class SuccessDto {
    private int total;
  }

  @Builder
  @Getter
  public static class ContentsDto {
    private String translated;
    private String text;
    private String translation;
  }
}
