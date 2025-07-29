package com.pokemonreview.api.Repository;

import com.pokemonreview.api.Models.Pokemon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PokemonRepository extends JpaRepository<Pokemon, Integer>
{

}
