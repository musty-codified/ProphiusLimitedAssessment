package com.prophiuslimited.ProphiusLimitedAssessment.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;


@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ErrorResponse {
    private Date timeStamp;
    private String message;
    private String debugMessage;

    public ErrorResponse(Date timeStamp, String message) {
        this.timeStamp = timeStamp;
        this.message = message;
    }
}
