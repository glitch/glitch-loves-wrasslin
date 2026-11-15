package dev.glitch.wrasslin.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import dev.glitch.wrasslin.model.AttendanceModel;
import dev.glitch.wrasslin.repository.AttendanceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Service
@Slf4j
public class AttendanceService {

    private final AttendanceRepository repo;

    public AttendanceModel saveRecord(AttendanceModel record) {
        record.setEmail(record.getEmail().toLowerCase());
        Optional<AttendanceModel> model = repo.findByEmailAndDateBetweenOrderByDateDesc(record.getEmail(), record.getDate(), record.getDate()).stream().findFirst();
        if (model.isEmpty()) {
            return repo.save(record.withId(null)); // zero out id since it's auto-gen        
        } else {
            log.info("Record already present {}", model);
            return model.get();
        }
    }

    public AttendanceModel updateRecord(AttendanceModel record) {
        record.setEmail(record.getEmail().toLowerCase());
        return repo.save(record); // Complete overwrite
    }

    public List<AttendanceModel> getAttendance(String email) {
        return repo.findByEmailOrderByDateDesc(email.toLowerCase());
    }

    public List<AttendanceModel> getAttendance(String email, LocalDate startDate, LocalDate endDate) {
        return repo.findByEmailAndDateBetweenOrderByDateDesc(email.toLowerCase(), startDate, endDate);
    }

    public List<AttendanceModel> getAttendanceByDate(LocalDate localDate) {
        return repo.findByDateOrderByDateDescEmailAsc(localDate);
    }

    public List<String> getWrestlers() {
        return repo.findDistinctEmail();
    }

    public void deleteRecord(Long id) {
        repo.deleteById(id);
    }

    public void deleteRecords(List<Long> ids) {
        repo.deleteAllById(ids);
    }

    public List<AttendanceModel> dumpDB() {
        return repo.findAllByOrderByDateDescEmailAsc();
    }
}
