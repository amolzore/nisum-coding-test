package com.amolzore.nisum.codingtest.dataaccess.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ZipCodeRange {
    private int lowerLimit;
    private int upperLimit;

    @Override
    public String toString() {
        return "[" + lowerLimit + "," + upperLimit + "]";
    }
}
