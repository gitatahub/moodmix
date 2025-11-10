package com.moodmix.moodmix.data.repositories;

import com.moodmix.moodmix.data.interfaces.ITrackRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.moodmix.moodmix.data.entities.Track;


@Repository
public interface TrackRepository extends JpaRepository<Track,Long>, ITrackRepository {


}
