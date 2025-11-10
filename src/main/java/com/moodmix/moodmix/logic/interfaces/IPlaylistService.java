package com.moodmix.moodmix.logic.interfaces;

import com.moodmix.moodmix.api.DTO.Playlist.CreatePlaylistRequest;
import com.moodmix.moodmix.api.DTO.Track.TrackResponse;
import com.moodmix.moodmix.api.DTO.Track.TrackUploadRequest;
import com.moodmix.moodmix.data.entities.Playlist;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface IPlaylistService {

    public Playlist createPlaylist(CreatePlaylistRequest request );

    public Optional<Playlist> findByName(String name);

    Playlist addTrack(Long playlistId, Long trackId);

    Playlist uploadTrack(Long playlistId, TrackUploadRequest request);

    void deletePlaylist(Long playlistId);

    Playlist removeTrack(Long playlistId, Long trackId);

    public Playlist getByIdOrThrow(Long id);

    List<TrackResponse> findAll();
}
