package com.onkonfeton;

import lombok.Data;
import lombok.Generated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class Person {
    private String name;
    private int age;
    private boolean isStudent;
    private List<String> courses;

}
