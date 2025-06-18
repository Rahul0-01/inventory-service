package com.gpstracker.inventory_service.exception; // THIS SHOULD MATCH YOUR PACKAGE

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.List; // For multiple validation errors

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
    private LocalDateTime timestamp;
    private int status; // HTTP status code
    private String error; // HTTP status reason phrase (e.g., "Not Found", "Bad Request")
    private String message; // Our custom, more specific error message or list of validation errors
    private String path; // The URL path where the error occurred

//    // Optional constructor for a single message
//    public ErrorResponse(LocalDateTime timestamp, int status, String error, String message, String path) {
//        this.timestamp = timestamp;
//        this.status = status;
//        this.error = error;
//        this.message = message;
//        this.path = path;
//    }

    // Optional: If you want to handle multiple validation messages slightly differently
    // you could have a List<String> messages field instead of a single String message.
    // For simplicity now, we'll combine validation messages into a single string.
}