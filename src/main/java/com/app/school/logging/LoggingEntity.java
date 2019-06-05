package com.app.school.logging;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "logger")
@Data
public class LoggingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String method;
    @Column(length = 512)
    private String message;
    private LocalDateTime dateTime;
}
