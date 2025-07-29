package com.pokemonreview.api.Controllers;

import com.pokemonreview.api.Dtos.ReviewDtos.ReviewDto;
import com.pokemonreview.api.Interface.IReviewService;
import com.pokemonreview.api.Models.Review;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/")
public class ReviewController
{
    private final IReviewService _reviewService;

    @Autowired
    public ReviewController(IReviewService reviewService) {
        _reviewService = reviewService;
    }

    @PostMapping("pokemon/{pokemonId}/reviews")
    public ResponseEntity<?> CreateReview(@RequestBody ReviewDto reviewDto)
    {
        var review = _reviewService.createReview(reviewDto.getPokemonId(), reviewDto);

        if(review == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pokemon not found");

        return ResponseEntity.ok(review);
    }
    @GetMapping("/pokemon/{pokemonId}/reviews")
    public ResponseEntity<?> GetReviewsByPokemonId(@PathVariable int pokemonId)
    {
        var reviews = _reviewService.getReviewsByPokemonId(pokemonId);
        if(reviews == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Reviews are not found");

        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/pokemon/{pokemonId}/reviews/{id}")
    public ResponseEntity<?> getReviewById(@PathVariable int pokemonId, @PathVariable int id)
    {
        ReviewDto reviewDto = _reviewService.getReviewById(id, pokemonId);
        if(reviewDto == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Review not found");

        return ResponseEntity.ok(reviewDto);
    }

    @PutMapping("/pokemon/{pokemonId}/reviews/{id}")
    public ResponseEntity<?> updateReviewById(@PathVariable int pokemonId, @PathVariable int id, @RequestBody ReviewDto reviewDto)
    {
        ReviewDto updatedReviewDto = _reviewService.updateReview(id,pokemonId,reviewDto);
        if(updatedReviewDto == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Review not found");

        return ResponseEntity.ok(updatedReviewDto);
    }

    @DeleteMapping("/pokemon/{pokemonId}/reviews/{id}")
    public ResponseEntity<?> deleteReview(@PathVariable int pokemonId, @PathVariable int id)
    {
        ReviewDto deletedReviewDto = _reviewService.deleteReview(id, pokemonId);

        if(deletedReviewDto == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Review not found");

        return ResponseEntity.ok(deletedReviewDto);
    }
}
