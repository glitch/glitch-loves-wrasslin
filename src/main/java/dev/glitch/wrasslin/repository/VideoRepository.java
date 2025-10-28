package dev.glitch.wrasslin.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import dev.glitch.wrasslin.model.VideoModel;

@Repository
public interface VideoRepository extends JpaRepository<VideoModel, Long> {

    @Query(value = "SELECT T.* FROM FT_SEARCH_DATA(?1, 0, 0) FT, VIDEO T WHERE FT.\"TABLE\"='VIDEO' AND T.ID=FT.\"KEYS\"[1];", nativeQuery = true)
    // @Query(value = "SELECT p.* FROM FTL_SEARCH_DATA(:searchTerm, 0, 0) ft, VIDEO p WHERE ft.TABLE='VIDEO' AND p.ID = ft.KEYS[0]", nativeQuery = true)
    List<VideoModel> searchByFullText(@Param("searchTerm") String searchTerm);

    @Query(value = "CALL FT_REINDEX()", nativeQuery = true)
    void reindex();

}
