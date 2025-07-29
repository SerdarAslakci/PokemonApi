package com.pokemonreview.api.Interface;

import com.pokemonreview.api.Dtos.PokemonsDtos.PokemonDto;

import java.util.List;

public interface IPokemonService
{
     PokemonDto createPokemon(PokemonDto pokemonDto);
     List<PokemonDto> getAllPokemons(int pageNumber, int pageSize);
     PokemonDto getPokemonById(Integer id);
     PokemonDto updatePokemon(PokemonDto pokemonDto,int id);
     PokemonDto deletePokemon(int id);

}
