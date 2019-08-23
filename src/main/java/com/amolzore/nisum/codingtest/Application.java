package com.amolzore.nisum.codingtest;

import com.amolzore.nisum.codingtest.service.ZipCodeRangeService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Application demo to reduce the zipCodeRanges with quique and minium zip code ranges.
 *
 * @author :- Amol Zore   
 */
public class Application {
    public static void main(String[] args) {
        List zipCodeRangeList = new ArrayList();
        Scanner scanner = new Scanner(System.in);
        Scanner input = new Scanner(scanner.nextLine());
        while (input.hasNext()) {
            zipCodeRangeList.add(input.next());
        }
        scanner.close();
        ZipCodeRangeService zipCodeRangeService = new ZipCodeRangeService();
        (zipCodeRangeService.getUinqueMinimumNumberOfRanges(zipCodeRangeList)).forEach(System.out::print);
    }
}
