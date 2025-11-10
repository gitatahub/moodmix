package com.moodmix.moodmix.data.repositories;

import com.moodmix.moodmix.data.entities.Playlist;
import com.moodmix.moodmix.data.interfaces.IPlaylistRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlaylistRepository extends JpaRepository<Playlist, Long>, IPlaylistRepository {
    Optional<Playlist> findByName(String name);


}
