package com.tujuhsembilan.app.exception;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@Builder
@NoArgsConstructor
@AllArgsConstructor 
public class NotFoundException {
     
  private String message;
  private int statusCode;
  private String status;
}
