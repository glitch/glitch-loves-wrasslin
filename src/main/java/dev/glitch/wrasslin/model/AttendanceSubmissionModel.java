package dev.glitch.wrasslin.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AttendanceSubmissionModel {
    private final String email;
    private final String code;
}
