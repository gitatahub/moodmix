package com.moodmix.moodmix.data.analysis;

import com.moodmix.moodmix.logic.interfaces.AudioAnalysisService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;

import java.io.File;
import java.util.Map;

@Service
public class EssentiaAnalysisService implements AudioAnalysisService {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${essentia.api.url}")
    private String Url;

    @Override
    public Map<String, Object> analyzeAudio(File file) {

        try {
            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            body.add("file", new FileSystemResource(file));

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);

            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

            ResponseEntity<Map> response = restTemplate.postForEntity(
                    Url, requestEntity, Map.class);

            return response.getBody();
        } catch (Exception e) {
            throw Problem.valueOf(Status.INTERNAL_SERVER_ERROR, e.getMessage() );
        }
    }
}
