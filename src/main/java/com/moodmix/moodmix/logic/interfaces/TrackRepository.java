package com.moodmix.moodmix.logic.interfaces;

import com.moodmix.moodmix.data.entities.Track;

import java.util.List;
import java.util.Optional;

public interface TrackRepository {
    Track save(Track track);
    Optional<Track> findById(Long id);
    List<Track> findAll();
}
