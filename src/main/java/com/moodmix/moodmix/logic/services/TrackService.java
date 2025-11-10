package com.moodmix.moodmix.logic.services;

import com.moodmix.moodmix.api.DTO.Track.TrackUploadRequest;
import com.moodmix.moodmix.data.entities.Track;
import com.moodmix.moodmix.data.interfaces.ITrackRepository;
import com.moodmix.moodmix.logic.interfaces.AudioAnalysisService;
import com.moodmix.moodmix.logic.interfaces.ITrackService;
import com.moodmix.moodmix.logic.interfaces.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TrackService implements ITrackService {

    private final ITrackRepository trackRepository;
    private final StorageService storageService;
    private final AudioAnalysisService audioAnalysisService;

    @Override
    public Track upload(TrackUploadRequest request) {
        MultipartFile file = request.file();
        validate(file);

        var stored = storageService.store(file);
        File storedFile = new File(stored.absolutePath());

        Map<String, Object> metadata = audioAnalysisService.analyzeAudio(storedFile);

        var track = new Track();
        track.setFilenameOrig(file.getOriginalFilename());
        track.setStoragePath(stored.relativePath());
        track.setContentType(file.getContentType());
        track.setFileSizeBytes(file.getSize());

        // Artist: use user input, else default
        if (request.artist() != null && !request.artist().isBlank()) {
            track.setArtist(request.artist());
        } else {
            track.setArtist("Unknown Artist");
        }

        // Title: use user input, else fallback to metadata/filename
        if (request.title() != null && !request.title().isBlank()) {
            track.setTitle(request.title());
        } else {
            track.setTitle((String) metadata.getOrDefault("title", file.getOriginalFilename()));
        }

        track.setDurationSec((Integer) metadata.getOrDefault("duration_seconds", 0));
        track.setBpm((Integer) metadata.getOrDefault("bpm", 0));
        track.setKey((String) metadata.getOrDefault("key", "Unknown"));
        track.setScale((String) metadata.getOrDefault("scale", "Unknown"));

        return trackRepository.save(track);
    }

    private void validate(MultipartFile f) {
        if (f == null || f.isEmpty()) {
            throw Problem.valueOf(Status.NOT_FOUND, "File is empty");
        }
        if (f.getSize() > 25 * 1024 * 1024) {
            throw Problem.valueOf(Status.NOT_ACCEPTABLE, "File is too large. Max allowed is 25MB");
        }
        String filename = f.getOriginalFilename();
        if (filename == null || !filename.contains(".")) {
            throw Problem.valueOf(Status.BAD_REQUEST, "File name is invalid");
        }

        String ext = filename.substring(filename.lastIndexOf(".") + 1).toLowerCase();
        if (!(ext.equals("mp3") || ext.equals("wav"))) {
            throw Problem.valueOf(Status.UNSUPPORTED_MEDIA_TYPE, "Only MP3 or WAV allowed");

        }
    }


    @Override
    public Optional<Track> getById(Long id) {
        return trackRepository.findById(id);
    }
    @Override
    public List<Track> getAll() {
        return trackRepository.findAll();
    }

}
