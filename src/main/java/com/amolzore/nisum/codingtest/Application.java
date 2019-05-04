package com.amolzore.nisum.codingtest;

import com.amolzore.nisum.codingtest.service.ZipCodeRangeService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Application Defination.
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
        (zipCodeRangeService.getUinqueMiniumNumberOfRanges(zipCodeRangeList)).forEach(System.out::print);

    }
}
