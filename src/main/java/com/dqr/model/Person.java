package com.dqr.model;

import lombok.Builder;
import lombok.Data;

/**
 * Created by dqromney on 3/14/17.
 */
@Data
@Builder
public class Person {

    String firstName, lastName, school;
    int rollNumber;
}
