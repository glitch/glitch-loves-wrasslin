package dev.glitch.wrasslin.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dev.glitch.wrasslin.model.VideoModel;
import dev.glitch.wrasslin.service.VideoService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/videos")
@RequiredArgsConstructor
public class VideoController {

    private final VideoService service;

    @GetMapping(value = "/{id}")
    public VideoModel getById(@PathVariable("id") Long id) {
        Optional<VideoModel> video = service.getVideoById(id);
        if (video.isPresent()) {
            return video.get();
        }
        return null;
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

    @GetMapping("/url")
    public VideoModel findByUrl(@RequestParam("url") String url) {
        return service.findByUrl(url);
    }

    @GetMapping("/urls")
    public List<String> findDistinctUrls() {
        return service.findDistinctUrls();
    }

    @GetMapping("/families")
    public List<String> findDistinctFamilies() {
        return service.findDistinctFamily();
    }

    @GetMapping("/positions")
    public List<String> findDistinctPositions() {
        return service.findDistinctPositions();
    }
}
