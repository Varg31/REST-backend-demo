package com.app.school.school_app.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.Set;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class CaptchaResponseDTO {
    private boolean success;

    @JsonAlias("error-codes")
    private Set<String> errorCodes;
}
