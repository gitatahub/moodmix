package com.moodmix.moodmix.logic.interfaces;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Map;

public interface AudioAnalysisService {
    Map<String, Object> analyzeAudio(File file);
}
