package com.moodmix.moodmix.data.interfaces;

import com.moodmix.moodmix.data.entities.Playlist;

import java.util.List;
import java.util.Optional;

public interface IPlaylistRepository {
    Optional<Playlist> findByName(String name);

    Playlist save(Playlist playlist);
    Optional<Playlist> findById(Long Id);

    void delete(Playlist playlist);


    List<Playlist> findAll();
}
