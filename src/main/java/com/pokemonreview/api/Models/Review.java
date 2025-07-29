package com.pokemonreview.api.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Review
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    private String content;
    private int start;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pokemonId")
    private Pokemon pokemon;

}
