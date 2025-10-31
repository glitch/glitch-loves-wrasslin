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

    public VideoModel saveRecord(VideoModel record) {
        return repo.save(record.withId(null)); // zero out id since it's auto-generated
    }

    public List<VideoModel> saveRecords(List<VideoModel> records) {
        List<VideoModel> cleaned = records.stream().map(c -> c.withId(null)).toList();
        return repo.saveAll(cleaned);
    }

    public List<VideoModel> saveRecordIfUniqueUrl(List<VideoModel> records) {
        List<VideoModel> unique = records.stream().filter(c -> null == repo.findDistinctByUrl(c.getUrl())).map(c -> c.withId(null)).toList();
        return repo.saveAll(unique);
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

    public VideoModel findByUrl(String url) {
        return repo.findDistinctByUrl(url);
    }

    public List<String> findDistinctUrls() {
        return repo.findDistinctUrl();
    }

    public List<String> findDistinctFamily() {
        return repo.findDistinctFamily();
    }

    public List<String> findDistinctPositions() {
        return repo.findDistinctPosition();
    }

    public VideoModel updateRecord(VideoModel record) {
        return repo.save(record);
    }

    public void deleteRecord(VideoModel record) {
        repo.delete(record);
    }

    public void deleteRecord(Long id) {
        repo.deleteById(id);
    }

    public void deleteRecords(List<VideoModel> records) {
        repo.deleteAll(records);
    }

    public void reindex() {
        repo.reindex();
    }

    public List<VideoModel> dumpDB() {
        return repo.findAll();
    }
}
