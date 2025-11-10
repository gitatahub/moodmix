package com.moodmix.moodmix.logic.interfaces;

import com.moodmix.moodmix.api.DTO.Track.TrackUploadRequest;
import com.moodmix.moodmix.data.entities.Track;

import java.util.List;
import java.util.Optional;

public interface ITrackService {
    public Track upload(TrackUploadRequest request);
    public Optional<Track> getById(Long id);
    public List<Track> getAll();



}
