package dev.glitch.wrasslin.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import dev.glitch.wrasslin.model.VideoModel;
import dev.glitch.wrasslin.repository.VideoRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class VideoService {

    private final VideoRepository repo;

    public Optional<VideoModel> getVideoById(Long id) {
        return repo.findById(id);
    }

    public Page<VideoModel> getPage(Pageable page) {
        return repo.findAll(page);
    }

    public VideoModel saveVideoClip(VideoModel clip) {
        return repo.save(clip.withId(null)); // zero out id since it's auto-generated
    }

    public List<VideoModel> saveVideoClips(List<VideoModel> clips) {
        List<VideoModel> cleaned = clips.stream().map(c -> c.withId(null)).toList();
        return repo.saveAll(cleaned);
    }

    public List<VideoModel> searchByFullText(String text) {
        return repo.searchByFullText(text);
    }

    public List<VideoModel> searchTagsByFullText(String text) {
        return repo.searchTagsByFullText(text);
    }

    public List<VideoModel> searchNotesByFullText(String text) {
        return repo.searchNotesByFullText(text);
    }

    public List<VideoModel> searchPositionByFullText(String text) {
        return repo.searchPositionByFullText(text);
    }

    public List<VideoModel> searchRelatedByFullText(String text) {
        return repo.searchRelatedByFullText(text);
    }

    public void reindex() {
        repo.reindex();
    }

    public List<VideoModel> dumpDB() {
        return repo.findAll();
    }
}
