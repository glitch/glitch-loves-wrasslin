package dev.glitch.wrasslin.repository;

import java.util.List;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import dev.glitch.wrasslin.model.VideoModel;

@Repository
@ConditionalOnProperty(name = "spring.datasource.platform", havingValue = "mysql")
public interface VideoRepositoryMySQL extends VideoRepository {

    @Query(value = "SELECT v.* FROM video v WHERE MATCH (v.family, v.notes, v.position, v.tags) AGAINST (:searchTerm IN NATURAL LANGUAGE MODE)", nativeQuery = true)
    // @Query(value = "SELECT * FROM video WHERE MATCH (family, notes, position, tags) AGAINST (:searchTerm IN BOOLEAN MODE)", nativeQuery = true)
    List<VideoModel> searchByFullText(@Param("searchTerm") String searchTerm);

    @Query(value = "SELECT v.* FROM video v WHERE MATCH (v.tags) AGAINST (:searchTerm IN NATURAL LANGUAGE MODE)", nativeQuery = true)
    List<VideoModel> searchTagsByFullText(@Param("searchTerm") String searchTerm);

    @Query(value = "SELECT v.* FROM video v WHERE MATCH (v.notes) AGAINST (:searchTerm IN NATURAL LANGUAGE MODE)", nativeQuery = true)
    List<VideoModel> searchNotesByFullText(@Param("searchTerm") String searchTerm);

    @Query(value = "SELECT v.* FROM video v WHERE MATCH (v.position) AGAINST (:searchTerm IN NATURAL LANGUAGE MODE)", nativeQuery = true)
    List<VideoModel> searchPositionByFullText(@Param("searchTerm") String searchTerm);

    @Query(value = "SELECT v.* FROM video v WHERE MATCH (v.family) AGAINST (:searchTerm IN NATURAL LANGUAGE MODE)", nativeQuery = true)
    List<VideoModel> searchFamilyByFullText(@Param("searchTerm") String searchTerm);

    @Query(value = "SELECT v.* FROM video v WHERE MATCH (v.related) AGAINST (:searchTerm IN NATURAL LANGUAGE MODE)", nativeQuery = true)
    List<VideoModel> searchRelatedByFullText(@Param("searchTerm") String searchTerm);

    @Query(value = "SELECT DISTINCT v.url FROM video v")
    List<String> findDistinctUrl();

    @Query(value = "SELECT DISTINCT v.family FROM video v")
    List<String> findDistinctFamily();

    @Query(value = "SELECT DISTINCT v.position FROM video v")
    List<String> findDistinctPosition();

    // Not supported, InnoDB should automatically be updating indices
    @Query(value = "SELECT 1;", nativeQuery = true)
    void reindex();
}
