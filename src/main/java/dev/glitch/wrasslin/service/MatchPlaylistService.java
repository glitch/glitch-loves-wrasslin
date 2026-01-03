package dev.glitch.wrasslin.service;

import java.util.List;

import org.springframework.stereotype.Service;

import dev.glitch.wrasslin.model.MatchPlaylistModel;
import dev.glitch.wrasslin.repository.MatchPlaylistRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MatchPlaylistService {
    private final MatchPlaylistRepository repo;

    public List<MatchPlaylistModel> getPlaylists() {
        // return repo.findAll(Sort.by(Sort.Direction.DESC));
        return repo.findAllByOrderByNameDesc();
    }

    public List<MatchPlaylistModel> getPlaylistsByYear(int year) {
        return repo.findAllByYearOrderByYearDesc(year);
    }

    public MatchPlaylistModel savePlaylist(MatchPlaylistModel playlist) {
        return repo.save(playlist.withId(null));
    }

    public List<MatchPlaylistModel> savePlaylists(List<MatchPlaylistModel> playlists) {
        List<MatchPlaylistModel> cleaned = playlists.stream().map(c -> c.withId(null)).toList();
        return repo.saveAll(cleaned);
    }

    public MatchPlaylistModel updatePlaylist(MatchPlaylistModel playlist) {
        return repo.save(playlist);
    }

    public void deleteRecord(Long id) {
        repo.deleteById(id);
    }
}
