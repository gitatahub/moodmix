package com.moodmix.moodmix.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.moodmix.moodmix.data.entities.Track;


@Repository
public interface SpringDataTrackRepository extends JpaRepository<Track,Long> {

}
