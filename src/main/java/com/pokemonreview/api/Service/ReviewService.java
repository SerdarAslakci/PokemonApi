package com.pokemonreview.api.Service;

import com.pokemonreview.api.Dtos.ReviewDtos.ReviewDto;
import com.pokemonreview.api.Interface.IReviewService;
import com.pokemonreview.api.Models.Pokemon;
import com.pokemonreview.api.Models.Review;
import com.pokemonreview.api.Repository.PokemonRepository;
import com.pokemonreview.api.Repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewService implements IReviewService
{

    private final PokemonRepository _pokemonRepository;
    private final ReviewRepository _reviewRepository;

    @Autowired
    public ReviewService(PokemonRepository pokemonRepository, ReviewRepository reviewRepository)
    {
        _pokemonRepository = pokemonRepository;
        _reviewRepository = reviewRepository;
    }

    @Override
    public ReviewDto createReview(int pokemonId, ReviewDto reviewDto)
    {
        Pokemon pokemon = _pokemonRepository.findById(pokemonId).orElse(null);
        if (reviewDto == null) {return  null;}

        Review review = toEntity(reviewDto);
        review.setPokemon(pokemon);

        Review saved = _reviewRepository.save(review);

        return toReviewDto(saved);
    }

    @Override
    public List<ReviewDto> getReviewsByPokemonId(int pokemonId) {
        List<Review> reviews = _reviewRepository.findByPokemonId(pokemonId);

        if(reviews.isEmpty()) return null;

        return reviews.stream().map(review -> toReviewDto(review)).collect(Collectors.toList());
    }

    @Override
    public ReviewDto getReviewById(int reviewId, int pokemonId) {
        Pokemon pokemon = _pokemonRepository.findById(pokemonId).orElse(null);
        if (pokemon == null) {return null;}

        Review review = _reviewRepository.findById(reviewId).orElse(null);

        if (review == null) {return null;}

        if (review.getPokemon().getId() != pokemonId) {return null;}

        return toReviewDto(review);
    }

    @Override
    public ReviewDto updateReview(int reviewId,int pokemonId, ReviewDto reviewDto)
    {
        Pokemon pokemon = _pokemonRepository.findById(pokemonId).orElse(null);
        if (pokemon == null) {return null;}

        Review review = _reviewRepository.findById(reviewId).orElse(null);

        if (review == null) {return null;}

        if (review.getPokemon().getId() != pokemonId) {return null;}

        review.setTitle(reviewDto.getTitle());
        review.setContent(reviewDto.getContent());
        review.setStart(reviewDto.getStart());

        Review saved = _reviewRepository.save(review);

        return toReviewDto(review);
    }

    @Override
    public ReviewDto deleteReview(int reviewId, int pokemonId) {
        Pokemon pokemon = _pokemonRepository.findById(pokemonId).orElse(null);
        if (pokemon == null) {return null;}

        Review review = _reviewRepository.findById(reviewId).orElse(null);

        if (review == null) {return null;}

        if (review.getPokemon().getId() != pokemonId) {return null;}

        _reviewRepository.delete(review);

        return toReviewDto(review);
    }

    private Review toEntity(ReviewDto reviewDto)
    {
        Review review = new Review();
        review.setTitle(reviewDto.getTitle());
        review.setContent(reviewDto.getContent());
        review.setStart(reviewDto.getStart());

        return review;
    }
    private ReviewDto toReviewDto(Review review)
    {
        ReviewDto reviewDto = new ReviewDto();
        reviewDto.setTitle(review.getTitle());
        reviewDto.setContent(review.getContent());
        reviewDto.setStart(review.getStart());
        reviewDto.setPokemonId(review.getPokemon().getId());

        return reviewDto;
    }
}
