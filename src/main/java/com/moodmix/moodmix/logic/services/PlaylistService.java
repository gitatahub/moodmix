package com.moodmix.moodmix.logic.services;

import com.moodmix.moodmix.api.DTO.Playlist.CreatePlaylistRequest;
import com.moodmix.moodmix.api.DTO.Track.TrackResponse;
import com.moodmix.moodmix.api.DTO.Track.TrackUploadRequest;
import com.moodmix.moodmix.data.entities.Playlist;
import com.moodmix.moodmix.data.entities.Track;
import com.moodmix.moodmix.data.interfaces.IPlaylistRepository;
import com.moodmix.moodmix.logic.interfaces.IPlaylistService;
import com.moodmix.moodmix.logic.interfaces.ITrackService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PlaylistService implements IPlaylistService {
    private final IPlaylistRepository playlistRepository;
    private final ITrackService trackService;

    @Override
    public Playlist createPlaylist(CreatePlaylistRequest request ) {
        Playlist playlist = new Playlist();
        playlist.setName(request.name());
        playlist.setDescription(request.description());

        return playlistRepository.save(playlist);
    }
    @Override
    public Playlist addTrack(Long playlistId, Long trackId) {
        Playlist playlist = playlistRepository.findById(playlistId)
                .orElseThrow(() -> Problem.valueOf(Status.NOT_FOUND, "Playlist not found"));

        Track track = trackService.getById(trackId)
                .orElseThrow(() -> Problem.valueOf(Status.NOT_FOUND, "Track not found"));

        playlist.getTracks().add(track);
        return playlistRepository.save(playlist);
    }

    @Override
    public Playlist uploadTrack(Long playlistId, TrackUploadRequest  file ) {
        Playlist playlist = playlistRepository.findById(playlistId)
                .orElseThrow(() -> Problem.valueOf(Status.NOT_FOUND, "Playlist not found"));

        Track uploadedTrack = trackService.upload(file);
        playlist.getTracks().add(uploadedTrack);
        return playlistRepository.save(playlist);
    }
    @Override
    public void deletePlaylist(Long playlistId) {
        Playlist playlist = playlistRepository.findById(playlistId)
                .orElseThrow(() -> Problem.valueOf(Status.NOT_FOUND, "Playlist not found"));
        playlistRepository.delete(playlist);
    }

    @Override
    public Playlist removeTrack(Long playlistId, Long trackId) {
        Playlist playlist = playlistRepository.findById(playlistId)
                .orElseThrow(() -> Problem.valueOf(Status.NOT_FOUND, "Playlist not found"));

        Track track = trackService.getById(trackId)
                .orElseThrow(() -> Problem.valueOf(Status.NOT_FOUND, "Track not found"));

        boolean removed = playlist.getTracks().removeIf(t -> t.getId().equals(track.getId()));

        if (!removed) {
            throw Problem.valueOf(Status.NOT_FOUND, "Track not found in playlist");
        }

        return playlistRepository.save(playlist);
    }
    @Override
    public Playlist getByIdOrThrow(Long id) {
        return playlistRepository.findById(id)
                .orElseThrow(() -> Problem.valueOf(Status.NOT_FOUND, "Playlist not found"));
    }



    @Override
    public Optional<Playlist> findByName(String name){
        return playlistRepository.findByName(name);
    }

    public List<TrackResponse> findAll(){

        return null;
    }

}

