package dev.glitch.wrasslin.controller;

import java.time.LocalDate;
import java.time.ZoneId;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import dev.glitch.wrasslin.model.AttendanceModel;
import dev.glitch.wrasslin.model.AttendanceSubmissionModel;
import dev.glitch.wrasslin.service.AttendanceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/attendance")
@RequiredArgsConstructor
@Slf4j
public class AttendanceController {

    private final AttendanceService service;
    private final ZoneId est = ZoneId.of("America/New_York");

    @Value(value = "${wrasslin.attendance.code:CODE_HERE}")
    private String authCode;

    @PostMapping(value = "/submit")
    @ResponseStatus(HttpStatus.CREATED)
    public AttendanceModel saveAttendance(@RequestBody AttendanceSubmissionModel sub) {
        log.info("sub: {}, authCode: {}", sub, authCode);
        if (isValid(sub)) {
            LocalDate localDate = LocalDate.now(est);
            AttendanceModel record = new AttendanceModel(null, sub.getEmail().toLowerCase(), localDate, true, null);
            return service.saveRecord(record);
        } else {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_CONTENT, "email empty or authCode invalid");
        }
    }

    private boolean isValid(AttendanceSubmissionModel model) {
        return null != model && StringUtils.hasText(model.getEmail()) && authCode.equalsIgnoreCase(model.getCode());
    }

}
