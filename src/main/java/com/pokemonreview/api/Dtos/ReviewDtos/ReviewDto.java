package com.pokemonreview.api.Dtos.ReviewDtos;

import lombok.Data;

@Data
public class ReviewDto
{
    private String title;
    private String content;
    private int start;
    private int pokemonId;
}
