package com.moodmix.moodmix.unitTest;

import com.moodmix.moodmix.api.DTO.Playlist.CreatePlaylistRequest;
import com.moodmix.moodmix.api.DTO.Track.TrackUploadRequest;
import com.moodmix.moodmix.data.entities.Playlist;
import com.moodmix.moodmix.data.entities.Track;
import com.moodmix.moodmix.data.interfaces.IPlaylistRepository;
import com.moodmix.moodmix.logic.exceptions.ServiceException;
import com.moodmix.moodmix.logic.interfaces.ITrackService;
import com.moodmix.moodmix.logic.services.PlaylistService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PlaylistServiceUnitTest {

    private IPlaylistRepository playlistRepository;
    private ITrackService trackService;
    private PlaylistService playlistService;

    @BeforeEach
    void setup() {
        playlistRepository = mock(IPlaylistRepository.class);
        trackService = mock(ITrackService.class);
        playlistService = new PlaylistService(playlistRepository, trackService);
    }

    // -----------------------------------------------------------------------------------
    // createPlaylist()
    // -----------------------------------------------------------------------------------

    @Test
    void createPlaylist_success() {
        CreatePlaylistRequest req = new CreatePlaylistRequest("Chill", "Relax vibes");

        Playlist saved = new Playlist();
        saved.setId(1L);
        saved.setName("Chill");
        saved.setDescription("Relax vibes");

        when(playlistRepository.save(any())).thenReturn(saved);

        Playlist result = playlistService.createPlaylist(req);

        assertEquals("Chill", result.getName());
        assertEquals("Relax vibes", result.getDescription());
        assertEquals(1L, result.getId());
    }

    @Test
    void createPlaylist_emptyName_throws() {
        CreatePlaylistRequest req = new CreatePlaylistRequest(" ", "desc");

        ServiceException ex = assertThrows(ServiceException.class,
                () -> playlistService.createPlaylist(req));

        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatus());
        assertEquals("Playlist name cannot be empty", ex.getMessage());
    }

    // -----------------------------------------------------------------------------------
    // addTrack()
    // -----------------------------------------------------------------------------------

    @Test
    void addTrack_success() {

        Playlist playlist = new Playlist();
        playlist.setId(1L);
        playlist.setTracks(new ArrayList<>());

        Track track = new Track();
        track.setId(10L);

        when(playlistRepository.findById(1L)).thenReturn(Optional.of(playlist));
        when(trackService.getById(10L)).thenReturn(Optional.of(track));
        when(playlistRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        Playlist result = playlistService.addTrack(1L, 10L);

        assertEquals(1, result.getTracks().size());
        assertEquals(10L, result.getTracks().get(0).getId());
    }

    @Test
    void addTrack_playlistNotFound() {
        when(playlistRepository.findById(1L)).thenReturn(Optional.empty());

        ServiceException ex = assertThrows(ServiceException.class,
                () -> playlistService.addTrack(1L, 10L));

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
        assertEquals("Playlist not found", ex.getMessage());
    }

    @Test
    void addTrack_trackNotFound() {
        Playlist playlist = new Playlist();
        playlist.setId(1L);
        playlist.setTracks(new ArrayList<>());

        when(playlistRepository.findById(1L)).thenReturn(Optional.of(playlist));
        when(trackService.getById(10L)).thenReturn(Optional.empty());

        ServiceException ex = assertThrows(ServiceException.class,
                () -> playlistService.addTrack(1L, 10L));

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
        assertEquals("Track not found", ex.getMessage());
    }

    @Test
    void addTrack_duplicateTrack_throws() {

        Track track = new Track();
        track.setId(10L);

        Playlist playlist = new Playlist();
        playlist.setId(1L);
        playlist.setTracks(new ArrayList<>(List.of(track)));

        when(playlistRepository.findById(1L)).thenReturn(Optional.of(playlist));
        when(trackService.getById(10L)).thenReturn(Optional.of(track));

        ServiceException ex = assertThrows(ServiceException.class,
                () -> playlistService.addTrack(1L, 10L));

        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatus());
        assertEquals("Track already exists in playlist", ex.getMessage());
    }

    // -----------------------------------------------------------------------------------
    // uploadTrack()
    // -----------------------------------------------------------------------------------

    @Test
    void uploadTrack_success() {
        Playlist playlist = new Playlist();
        playlist.setId(1L);
        playlist.setTracks(new ArrayList<>());

        Track uploaded = new Track();
        uploaded.setId(99L);

        TrackUploadRequest req = mock(TrackUploadRequest.class);

        when(playlistRepository.findById(1L)).thenReturn(Optional.of(playlist));
        when(trackService.upload(req)).thenReturn(uploaded);
        when(playlistRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        Playlist updated = playlistService.uploadTrack(1L, req);

        assertEquals(1, updated.getTracks().size());
        assertEquals(99L, updated.getTracks().get(0).getId());
    }

    @Test
    void uploadTrack_playlistNotFound() {
        TrackUploadRequest req = mock(TrackUploadRequest.class);

        when(playlistRepository.findById(1L)).thenReturn(Optional.empty());

        ServiceException ex = assertThrows(ServiceException.class,
                () -> playlistService.uploadTrack(1L, req));

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
        assertEquals("Playlist not found", ex.getMessage());
    }

    @Test
    void uploadTrack_duplicate_throws() {

        TrackUploadRequest req = mock(TrackUploadRequest.class);

        Track uploaded = new Track();
        uploaded.setId(100L);

        Playlist playlist = new Playlist();
        playlist.setId(1L);
        playlist.setTracks(new ArrayList<>(List.of(uploaded)));

        when(playlistRepository.findById(1L)).thenReturn(Optional.of(playlist));
        when(trackService.upload(req)).thenReturn(uploaded);

        ServiceException ex = assertThrows(ServiceException.class,
                () -> playlistService.uploadTrack(1L, req));

        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatus());
        assertEquals("Track already exists in playlist", ex.getMessage());
    }

    // -----------------------------------------------------------------------------------
    // removeTrack()
    // -----------------------------------------------------------------------------------

    @Test
    void removeTrack_success() {

        Track track = new Track();
        track.setId(10L);

        Playlist playlist = new Playlist();
        playlist.setId(1L);
        playlist.setTracks(new ArrayList<>(List.of(track)));

        when(playlistRepository.findById(1L)).thenReturn(Optional.of(playlist));
        when(trackService.getById(10L)).thenReturn(Optional.of(track));
        when(playlistRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        Playlist updated = playlistService.removeTrack(1L, 10L);

        assertTrue(updated.getTracks().isEmpty());
    }

    @Test
    void removeTrack_playlistNotFound() {
        when(playlistRepository.findById(1L)).thenReturn(Optional.empty());

        ServiceException ex = assertThrows(ServiceException.class,
                () -> playlistService.removeTrack(1L, 5L));

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
        assertEquals("Playlist not found", ex.getMessage());
    }

    @Test
    void removeTrack_trackNotFound() {

        Playlist playlist = new Playlist();
        playlist.setId(1L);
        playlist.setTracks(new ArrayList<>());

        when(playlistRepository.findById(1L)).thenReturn(Optional.of(playlist));
        when(trackService.getById(5L)).thenReturn(Optional.empty());

        ServiceException ex = assertThrows(ServiceException.class,
                () -> playlistService.removeTrack(1L, 5L));

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
        assertEquals("Track not found", ex.getMessage());
    }

    @Test
    void removeTrack_trackNotInPlaylist_throws() {

        Track track = new Track();
        track.setId(10L);

        Playlist playlist = new Playlist();
        playlist.setId(1L);
        playlist.setTracks(new ArrayList<>());

        when(playlistRepository.findById(1L)).thenReturn(Optional.of(playlist));
        when(trackService.getById(10L)).thenReturn(Optional.of(track));

        ServiceException ex = assertThrows(ServiceException.class,
                () -> playlistService.removeTrack(1L, 10L));

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
        assertEquals("Track not in playlist", ex.getMessage());
    }

    // -----------------------------------------------------------------------------------
    // getAllPlaylists()
    // -----------------------------------------------------------------------------------

    @Test
    void getAllPlaylists_success() {
        List<Playlist> mockList = List.of(new Playlist(), new Playlist());

        when(playlistRepository.findAll()).thenReturn(mockList);

        List<Playlist> result = playlistService.getAllPlaylists();

        assertEquals(2, result.size());
    }
}
