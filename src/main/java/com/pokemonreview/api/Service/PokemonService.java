package com.pokemonreview.api.Service;

import com.pokemonreview.api.Dtos.PokemonsDtos.PokemonDto;
import com.pokemonreview.api.Interface.IPokemonService;
import com.pokemonreview.api.Models.Pokemon;
import com.pokemonreview.api.Repository.PokemonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PokemonService implements IPokemonService
{
    private final PokemonRepository _pokemonRepository;

    @Autowired
    public PokemonService(PokemonRepository pokemonRepository) {
        _pokemonRepository = pokemonRepository;
    }

    @Override
    public PokemonDto createPokemon(PokemonDto pokemonDto) {
        Pokemon pokemon = new Pokemon();
        pokemon.setName(pokemonDto.getName());
        pokemon.setType(pokemonDto.getType());

        Pokemon newPokemon = _pokemonRepository.save(pokemon);

        PokemonDto pokemonResponse = new PokemonDto();
        pokemonResponse.setName(newPokemon.getName());
        pokemonResponse.setType(newPokemon.getType());

        return pokemonResponse;
    }

    @Override
    public List<PokemonDto> getAllPokemons(int pageNumber, int pageSize) {

        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Pokemon> pokemons = _pokemonRepository.findAll(pageable);
        List<Pokemon> listOfPokemons = pokemons.getContent();

        return listOfPokemons.stream().map(p -> mapToDto(p)).collect(Collectors.toList());
    }

    @Override
    public PokemonDto getPokemonById(Integer id) {
        Pokemon pokemon = _pokemonRepository.findById(id).orElse(null);
        if (pokemon == null) return null;

        return mapToDto(pokemon);
    }

    @Override
    public PokemonDto updatePokemon(PokemonDto pokemonDto, int id) {
         Pokemon pokemon = _pokemonRepository.findById(id).orElse(null);

         if (pokemon == null) return null;

         pokemon.setName(pokemonDto.getName());
         pokemon.setType(pokemonDto.getType());
         Pokemon updatedPokemon = _pokemonRepository.save(pokemon);

         return mapToDto(updatedPokemon);
    }

    @Override
    public PokemonDto deletePokemon(int id) {
        Pokemon pokemon =  _pokemonRepository.findById(id).orElse(null);
        if (pokemon == null) return null;
        _pokemonRepository.delete(pokemon);
        return mapToDto(pokemon);
    }

    private PokemonDto mapToDto(Pokemon pokemon)
    {
        PokemonDto dto = new PokemonDto();
        dto.setName(pokemon.getName());
        dto.setType(pokemon.getType());
        return dto;
    }

    private Pokemon mapToEntity (PokemonDto pokemonDto)
    {
        Pokemon pokemon = new Pokemon();
        pokemon.setName(pokemonDto.getName());
        pokemon.setType(pokemonDto.getType());

        return pokemon;
    }
}
