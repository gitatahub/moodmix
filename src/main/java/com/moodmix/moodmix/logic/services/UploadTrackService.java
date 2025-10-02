package com.moodmix.moodmix.logic.services;

import com.moodmix.moodmix.data.entities.Track;
import com.moodmix.moodmix.logic.interfaces.AudioAnalysisService;
import com.moodmix.moodmix.logic.interfaces.ITrackRepository;
import com.moodmix.moodmix.logic.interfaces.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor


public class UploadTrackService {

    private final ITrackRepository trackRepository;
    private final StorageService storageService;
    private final AudioAnalysisService audioAnalysisService;

    public Track upload(MultipartFile file) {
        validate(file);

        var stored = storageService.store(file);
        File storedFile = new File(stored.absolutePath());

        Map<String, Object> metadata = audioAnalysisService.analyzeAudio(storedFile);

        String filename = file.getOriginalFilename();
        String title = stripExt(filename);


        var track = new Track();
        track.setFilenameOrig(filename);
        track.setStoragePath(stored.relativePath());
        track.setContentType(file.getContentType());
        track.setFileSizeBytes(file.getSize());
        track.setArtist("Unknown Artist");
        track.setTitle((String) metadata.getOrDefault("title", file.getOriginalFilename()));
        track.setDurationSec((Integer) metadata.getOrDefault("duration_seconds", 0));
        track.setBpm((Integer) metadata.getOrDefault("bpm", 0));
        track.setKey((String) metadata.getOrDefault("key", "Unknown"));
        track.setScale((String) metadata.getOrDefault("scale", "Unknown"));

        return trackRepository.save(track);
    }

    private void validate(MultipartFile f) {
        if (f == null || f.isEmpty()) {
            throw new IllegalArgumentException("File is empty");
        }
        if (f.getSize() > 25 * 1024 * 1024) {
            throw new IllegalArgumentException("Max 25 MB");
        }
        String ct = f.getContentType() == null ? "" : f.getContentType();
        if (!ct.equals("audio/mpeg") && !ct.equals("audio/wav")) {
            throw new IllegalArgumentException("Only MP3 or WAV allowed");
        }
    }

    private String stripExt(String name) {
        if (name == null) return "Untitled";
        int dot = name.lastIndexOf('.');
        boolean isDot = dot >= 0;
        if (isDot) {
            return name.substring(0, dot);
        }
        return name;
    }


    public Optional<Track> getById(Long id) {
        return trackRepository.findById(id);
    }
}
