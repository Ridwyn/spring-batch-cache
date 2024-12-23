package com.example.batchprocessing;


import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
public class Person2 implements Serializable {

  String personId;
  String firstName;
  String lastName;
}
