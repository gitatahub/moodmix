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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = true)
    private User user;

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

    @Column(nullable = true)
    private int bpm;

    @Column(nullable = true)
    private String key;

    @Column(nullable = true)
    private String scale;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Instant createdAt;
}
