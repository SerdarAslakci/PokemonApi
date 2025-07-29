package com.pokemonreview.api.Interface;

import com.pokemonreview.api.Dtos.ReviewDtos.ReviewDto;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface IReviewService
{
    ReviewDto createReview(int pokemonId, ReviewDto reviewDto);
    List<ReviewDto> getReviewsByPokemonId(int pokemonId);
    ReviewDto getReviewById(int reviewId, int pokemonId);
    ReviewDto updateReview(int reviewId, int pokemonId, ReviewDto reviewDto);
    ReviewDto deleteReview(int reviewId, int pokemonId);


}
