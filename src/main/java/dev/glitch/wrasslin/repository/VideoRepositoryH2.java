package dev.glitch.wrasslin.repository;

import java.util.List;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import dev.glitch.wrasslin.model.VideoModel;

@Repository
@ConditionalOnProperty(name = "spring.datasource.platform", havingValue = "h2")
public interface VideoRepositoryH2 extends VideoRepository {

    @Query(value = "SELECT T.* FROM FT_SEARCH_DATA(?1, 0, 0) FT, VIDEO T WHERE FT.\"TABLE\"='VIDEO' AND T.ID=FT.\"KEYS\"[1];", nativeQuery = true)
    List<VideoModel> searchByFullText(@Param("searchTerm") String searchTerm);

    // H2 doesn't have the flexibility to do column specific stuff... or at least I haven't figured it out yet
    List<VideoModel> searchTagsByFullText(@Param("searchTerm") String searchTerm);

    List<VideoModel> searchNotesByFullText(@Param("searchTerm") String searchTerm);

    List<VideoModel> searchPositionByFullText(@Param("searchTerm") String searchTerm);

    List<VideoModel> searchFamilyByFullText(@Param("searchTerm") String searchTerm);

    List<VideoModel> searchRelatedByFullText(@Param("searchTerm") String searchTerm);

    @Query(value = "CALL FT_REINDEX()", nativeQuery = true)
    void reindex();

}
