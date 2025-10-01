package com.moodmix.moodmix.data.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import java.time.Instant;

@Entity
@Table(name = "tracks")
@Getter
@Setter
@NoArgsConstructor

public class Track {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // keep for later when auth is added
    @Column(nullable = false)
    private Long userId = 0L;

    // original uploaded filename (what the user had on disk)
    @Column(nullable = false)
    private String filenameOrig;

    // where we stored it (relative path or URL)
    @Column(nullable = false)
    private String storagePath;

    @Column(nullable = false, length = 100)
    private String contentType; // e.g., audio/mpeg, audio/wav

    @Column(nullable = false)
    private long fileSizeBytes;

    // extracted or fallback from filename
    @Column(nullable = false, length = 255)
    private String title;

    @Column(nullable = false, length = 255)
    private String artist;

    @Column(nullable = false)
    private int durationSec; // in seconds

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Instant createdAt;
}
