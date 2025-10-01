package com.moodmix.moodmix.data.implementation;

import com.moodmix.moodmix.data.entities.Track;
import com.moodmix.moodmix.data.repositories.TrackRepository;
import com.moodmix.moodmix.logic.interfaces.ITrackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TrackRepositoryImpl implements ITrackRepository {

    private final TrackRepository trackRepository;

    @Override
    public Track save(Track track) {
        return trackRepository.save(track);
    }
    @Override
    public Optional<Track> findById(Long id) {
        return trackRepository.findById(id);
    }
    @Override
    public List<Track> findAll() {
        return trackRepository.findAll();
    }

}
