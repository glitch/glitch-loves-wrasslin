package dev.glitch.wrasslin.controller;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import dev.glitch.wrasslin.model.VideoModel;
import dev.glitch.wrasslin.service.VideoService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/videos")
@RequiredArgsConstructor
public class VideoController {

    private final ObjectMapper mapper = new ObjectMapper();
    private final VideoService service;

    @GetMapping(value = "/{id}")
    public VideoModel getById(@PathVariable("id") Long id) {
        Optional<VideoModel> video = service.getVideoById(id);
        if (video.isPresent()) {
            return video.get();
        }
        return null;
    }

    @PostMapping("/loadone")
    @ResponseStatus(HttpStatus.CREATED)
    public VideoModel createVideo(@RequestBody VideoModel video) {
        return service.saveVideoClip(video);
    }

    @PostMapping("/load")
    @ResponseStatus(HttpStatus.CREATED)
    public List<VideoModel> createVideos(@RequestBody List<VideoModel> videos) {
        return service.saveVideoClips(videos);
    }

    @GetMapping("/findAll")
    public Page<VideoModel> findAll(Pageable pageable) {
        return service.getPage(pageable);
    }

    @GetMapping("/search")
    public List<VideoModel> search(@RequestParam("text") String text) {
        return service.searchByFullText(text);
    }

    @GetMapping("/searchTags")
    public List<VideoModel> searchTags(@RequestParam("text") String text) {
        return service.searchTagsByFullText(text);
    }

    @GetMapping("/searchNotes")
    public List<VideoModel> searchNotesTags(@RequestParam("text") String text) {
        return service.searchNotesByFullText(text);
    }

    @GetMapping("/searchPosition")
    public List<VideoModel> searchPositionTags(@RequestParam("text") String text) {
        return service.searchPositionByFullText(text);
    }

    @GetMapping("/searchRelated")
    public List<VideoModel> searchRelatedTags(@RequestParam("text") String text) {
        return service.searchRelatedByFullText(text);
    }

    /**
     * Currently needed to trigger H2 full-text indexing after initial data load.
     */
    @GetMapping("/reindex")
    public void reindex() {
        service.reindex();
    }

    @GetMapping("/download/json")
    public ResponseEntity<String> downloadJson() throws Exception {
        String allRecords = mapper.writeValueAsString(service.dumpDB());
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=wrasslin_records.json");
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setContentLength(allRecords.getBytes(StandardCharsets.UTF_8).length);

        return ResponseEntity.ok().headers(headers).body(allRecords);
    }
}
