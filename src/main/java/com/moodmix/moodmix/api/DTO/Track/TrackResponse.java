package com.moodmix.moodmix.api.DTO.Track;

import com.moodmix.moodmix.data.entities.Track;

public record TrackResponse(
        Long id,
        String title,
        String artist,
        int durationSec,
        int bpm,
        String key,
        String scale

) {
    public static TrackResponse fromEntity(Track track) {
        return new TrackResponse(
                track.getId(),
                track.getTitle(),
                track.getArtist(),
                track.getDurationSec(),
                track.getBpm(),
                track.getKey(),
                track.getScale()
        );
    }

}
