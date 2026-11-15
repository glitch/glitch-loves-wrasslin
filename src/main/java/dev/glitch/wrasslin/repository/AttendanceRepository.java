package dev.glitch.wrasslin.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import dev.glitch.wrasslin.model.AttendanceModel;

@Repository
@ConditionalOnProperty(name = "spring.datasource.platform", havingValue = "mysql")
public interface AttendanceRepository extends JpaRepository<AttendanceModel, Long> {

    List<AttendanceModel> findByDateBetweenOrderByDateDescEmailAsc(LocalDate startDate, LocalDate endDate);

    List<AttendanceModel> findByEmailAndDateBetweenOrderByDateDesc(String email, LocalDate startDate, LocalDate endDate);

    List<AttendanceModel> findByEmailOrderByDateDesc(String email);

    List<AttendanceModel> findByDateOrderByDateDescEmailAsc(LocalDate date);

    @Query("SELECT DISTINCT o.email FROM attendance o WHERE o.email IS NOT NULL ORDER BY o.email DESC")
    List<String> findDistinctEmail();

    List<AttendanceModel> findAllByOrderByDateDescEmailAsc();
}
