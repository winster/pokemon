package com.example.pokemon.dto;

import lombok.Getter;

@Getter
public class TranslationDto {
  private SuccessDto success;
  private ContentsDto contents;

  @Getter
  public static class SuccessDto {
    private int total;
  }

  @Getter
  public static class ContentsDto {
    private String translated;
    private String text;
    private String translation;
  }
}
