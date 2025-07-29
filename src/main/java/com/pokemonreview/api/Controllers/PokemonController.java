package com.pokemonreview.api.Controllers;

import com.pokemonreview.api.Dtos.PokemonsDtos.PokemonDto;
import com.pokemonreview.api.Interface.IPokemonService;
import com.pokemonreview.api.Models.Pokemon;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/")
public class PokemonController
{
    private final IPokemonService _pokemonService;

    public PokemonController(IPokemonService _pokemonService) {
        this._pokemonService = _pokemonService;
    }

    @GetMapping("pokemon")
    public ResponseEntity<List<PokemonDto>> GetPokemons(
            @RequestParam(value = "pageNumber",defaultValue = "0",required = false) int pageNumber,
            @RequestParam(value = "pageSize",defaultValue = "10",required = false) int pageSize
    )
    {
        List<PokemonDto> pokemonDtos = _pokemonService.getAllPokemons(pageNumber, pageSize);

        if(pokemonDtos.isEmpty())
            ResponseEntity.status(HttpStatus.NOT_FOUND).body("No pokemons are found");

        return ResponseEntity.ok(pokemonDtos);
    }

    @GetMapping("pokemon/{id}")
    public ResponseEntity<?> GetPokemonById(@PathVariable int id)
    {
        PokemonDto pokemonDto = _pokemonService.getPokemonById(id);

        if(pokemonDto == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pokemon Not Found:"+id);

        return ResponseEntity.ok(pokemonDto);
    }

    @PostMapping("pokemon/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<PokemonDto> CreatePokemon(@RequestBody PokemonDto pokemonDto)
    {
        return new ResponseEntity<>(_pokemonService.createPokemon(pokemonDto), HttpStatus.CREATED);
    }

    @PutMapping("pokemon/{id}/update")
    public ResponseEntity<PokemonDto> UpdatePokemon(@RequestBody PokemonDto pokemonDto, @PathVariable int id)
    {
        PokemonDto updatedProduct = _pokemonService.updatePokemon(pokemonDto,id);

        if (updatedProduct == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("pokemon/{id}/delete")
    public ResponseEntity<PokemonDto> DeletePokemon(@PathVariable int id)
    {
        PokemonDto deletedPokemon = _pokemonService.deletePokemon(id);
        if(deletedPokemon == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        return ResponseEntity.ok(deletedPokemon);
    }







}
