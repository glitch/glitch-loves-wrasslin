package dev.glitch.wrasslin.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

@Data
@NoArgsConstructor
@AllArgsConstructor
@With 
@Entity(name = "attendance")
public class AttendanceModel {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id = null;

    private String email = null;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "event_date")
    private LocalDate date = null;

    private Boolean present = true;
    private Boolean excused = null;
}
