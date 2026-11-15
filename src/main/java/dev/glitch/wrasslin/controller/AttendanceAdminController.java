package dev.glitch.wrasslin.controller;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import dev.glitch.wrasslin.model.AttendanceModel;
import dev.glitch.wrasslin.service.AttendanceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import tools.jackson.databind.json.JsonMapper;

@RestController
@RequestMapping("/admin/attendance")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
@Slf4j
public class AttendanceAdminController {

    private final JsonMapper mapper = JsonMapper.shared();
    private final ZoneId est = ZoneId.of("America/New_York");

    private final AttendanceService service;

    @GetMapping("/download/json")
    public ResponseEntity<String> downloadJson() throws Exception {
        String allRecords = mapper.writeValueAsString(service.dumpDB());
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=wrasslin_records.json");
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setContentLength(allRecords.getBytes(StandardCharsets.UTF_8).length);

        return ResponseEntity.ok().headers(headers).body(allRecords);
    }

    @GetMapping("/all")
    public List<AttendanceModel> findAll() {
        return service.dumpDB();
    }

    @GetMapping("/date/{ymd}")
    public List<AttendanceModel> findByDate(
            @PathVariable("ymd") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return service.getAttendanceByDate(date);
    }

    @GetMapping("/today/records")
    public List<AttendanceModel> findToday() {
        LocalDate today = LocalDate.now(est);
        return service.getAttendanceByDate(today);
    }

    @GetMapping("/today")
    public List<String> findTodayEmail() {
        LocalDate today = LocalDate.now(est);
        return service.getAttendanceByDate(today).stream().map(r -> r.getEmail()).toList();
    }

    @PostMapping("/wrestler")
    public List<AttendanceModel> findByEmail(@RequestBody String email) {
        return service.getAttendance(email);
    }

    @PostMapping("/submit")
    public AttendanceModel saveAttendance(@RequestBody AttendanceModel model) {
        return service.saveRecord(model);
    }

    @PostMapping("/update")
    public AttendanceModel updateAttendance(@RequestBody AttendanceModel model) {
        return service.updateRecord(model);
    }

    @DeleteMapping(value = "/{id}")
    public void deleteById(@PathVariable("id") Long id) {
        service.deleteRecord(id);
    }
}
