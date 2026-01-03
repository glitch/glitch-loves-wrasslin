package dev.glitch.wrasslin.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.glitch.wrasslin.model.MatchPlaylistModel;

public interface MatchPlaylistRepository extends JpaRepository<MatchPlaylistModel, Long> {
    
    MatchPlaylistModel findDistinctByUrl(String url);

    List<MatchPlaylistModel> findAllByYear(int year);

    List<MatchPlaylistModel> findAllByYearOrderByYearDesc(int year);

    List<MatchPlaylistModel> findAllByOrderByNameDesc();
}
