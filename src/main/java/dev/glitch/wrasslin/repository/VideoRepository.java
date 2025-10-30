package dev.glitch.wrasslin.repository;

import java.util.List;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBooleanProperty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import dev.glitch.wrasslin.model.VideoModel;

@ConditionalOnBooleanProperty(name = "noprop")
public interface VideoRepository extends JpaRepository<VideoModel, Long> {

    List<VideoModel> searchByFullText(@Param("searchTerm") String searchTerm);

    List<VideoModel> searchTagsByFullText(@Param("searchTerm") String searchTerm);

    List<VideoModel> searchNotesByFullText(@Param("searchTerm") String searchTerm);

    List<VideoModel> searchPositionByFullText(@Param("searchTerm") String searchTerm);

    List<VideoModel> searchFamilyByFullText(@Param("searchTerm") String searchTerm);

    List<VideoModel> searchRelatedByFullText(@Param("searchTerm") String searchTerm);

    VideoModel findDistinctByUrl(String url);

    List<String> findDistinctUrl();

    void reindex();
}
