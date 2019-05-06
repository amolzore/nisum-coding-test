package com.amolzore.nisum.codingtest.service;

import com.amolzore.nisum.codingtest.dataaccess.model.ZipCodeRange;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@RunWith(PowerMockRunner.class)
public class ZipCodeRangeServiceTest {

    @InjectMocks
    private ZipCodeRangeService zipCodeRangeService;

    @Test
    public void testGetUinqueMiniumNumberOfRangesWithNoOverLapping() {
        List ranges = new ArrayList();
        ranges.add("[94133,94133]");
        ranges.add("[94200,94299]");
        ranges.add("[94600,94699]");
        Assert.assertEquals(expectedZipCodeString(ranges),actualZipCodeString(zipCodeRangeService.getUinqueMiniumNumberOfRanges(ranges)));
    }

    @Test
    public void testGetUinqueMiniumNumberOfRangesWithOverLapping() {
        List ranges = new ArrayList();
        ranges.add("[94133,94133]");
        ranges.add("[94200,94299]");
        ranges.add("[94226,94399]");

        List actualRanges = new ArrayList();
        actualRanges.add("[94133,94133]");
        actualRanges.add("[94200,94399]");

        Assert.assertEquals(expectedZipCodeString(actualRanges),actualZipCodeString(zipCodeRangeService.getUinqueMiniumNumberOfRanges(ranges)));
    }

    @Test
    public void testGetUinqueMiniumNumberOfRangesFromSameRanges() {
        List ranges = new ArrayList();
        ranges.add("[44009,44009]");
        ranges.add("[44009,44009]");

        List actualRanges = new ArrayList();
        actualRanges.add("[44009,44009]");

        Assert.assertEquals(expectedZipCodeString(actualRanges),actualZipCodeString(zipCodeRangeService.getUinqueMiniumNumberOfRanges(ranges)));
    }

    @Test
    public void testgetUinqueMiniumNumberOfRangesForOneUinqueRange() {
        List ranges = new ArrayList();
        ranges.add("[44000,44000]");
        ranges.add("[55001,55099]");
        ranges.add("[33000,66000]");

        List actualRanges = new ArrayList();
        actualRanges.add("[33000,66000]");

        Assert.assertEquals(expectedZipCodeString(actualRanges),actualZipCodeString(zipCodeRangeService.getUinqueMiniumNumberOfRanges(ranges)));
    }

    @Test
    public void testGetUinqueMiniumNumberOfRangesWithArbitraryRanges() {
        List ranges = new ArrayList();
        ranges.add("[44009,33009]");
        ranges.add("[32999,44999]");
        ranges.add("[32666,32999]");
        ranges.add("[32777,30000]");
        ranges.add("[33444,33220]");
        ranges.add("[32110,33112]");

        List actualRanges = new ArrayList();
        actualRanges.add("[30000,44999]");

        Assert.assertEquals(expectedZipCodeString(actualRanges),actualZipCodeString(zipCodeRangeService.getUinqueMiniumNumberOfRanges(ranges)));
    }

    @Test
    public void testGetUinqueMiniumNumberOfRangesWithSpecialCharInZipCodeRanges() {
        List ranges = new ArrayList();
        ranges.add("[94133,94133]");
        ranges.add("[94200,94299]");
        ranges.add("[94226,94399]");
        ranges.add("[94226,94399]");
        ranges.add("[94226$$$$,94399$%]");

        List actualRanges = new ArrayList();
        actualRanges.add("[94133,94133]");
        actualRanges.add("[94200,94399]");

        Assert.assertEquals(expectedZipCodeString(actualRanges),actualZipCodeString(zipCodeRangeService.getUinqueMiniumNumberOfRanges(ranges)));
    }

    @Test
    public void testGetUinqueMiniumNumberOfRangesWithIncorrectLengthOfZipCodeRanges() {
        List ranges = new ArrayList();
        ranges.add("[94133,94133]");
        ranges.add("[94200,94299]");
        ranges.add("[94226,94399]");
        ranges.add("[94226,94399]");
        ranges.add("[226,943]");
        ranges.add("[226,943888888]");
        ranges.add("[00000000,943888888]");

        List actualRanges = new ArrayList();
        actualRanges.add("[94133,94133]");
        actualRanges.add("[94200,94399]");

        Assert.assertEquals(expectedZipCodeString(actualRanges),actualZipCodeString(zipCodeRangeService.getUinqueMiniumNumberOfRanges(ranges)));
    }

    @Test
    public void testGetUinqueMiniumNumberOfRangesWithMixIncorrectZipCodes() {
        List ranges = new ArrayList();
        ranges.add("[94133,94133]");
        ranges.add("[94200,94299]");
        ranges.add("[94226,94399]");
        ranges.add("[94226,94399]");
        ranges.add("926,94399]");
        ranges.add("926,94");
        ranges.add("226,943]");
        ranges.add("[226,943888888]");
        ranges.add("[00000000,943888888]");
        ranges.add("~~~~926,94%%%%");

        List actualRanges = new ArrayList();
        actualRanges.add("[94133,94133]");
        actualRanges.add("[94200,94399]");

        Assert.assertEquals(expectedZipCodeString(actualRanges),actualZipCodeString(zipCodeRangeService.getUinqueMiniumNumberOfRanges(ranges)));
    }

    @Test
    public void testGetUinqueMiniumNumberOfRangesWithOneHundredThousandZipCodeRange() throws InterruptedException, IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("zip-code-range-one-hundred-thousand.txt").getFile());
        String content = new String(Files.readAllBytes(file.toPath()));
        List ranges = new ArrayList();
        Scanner input = new Scanner(content);
        while (input.hasNext()) {
            ranges.add(input.next());
        }

        long startTime = System.nanoTime();

        zipCodeRangeService.getUinqueMiniumNumberOfRanges(ranges);

        long endTime = System.nanoTime();
        long timeElapsed = endTime - startTime;

        System.out.println("Execution time in milliseconds : " + timeElapsed / 1000000);
        Assert.assertNotNull(timeElapsed);
    }

    @Test
    public void testGetUinqueMiniumNumberOfRangesWithOneHundredThousandCorrectPatternZipCodeRange() throws InterruptedException, IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("zip-code-range-one-hundred-thousand-correct-pattern.txt").getFile());
        String content = new String(Files.readAllBytes(file.toPath()));
        List ranges = new ArrayList();
        Scanner input = new Scanner(content);
        while (input.hasNext()) {
            ranges.add(input.next());
        }

        long startTime = System.nanoTime();

        zipCodeRangeService.getUinqueMiniumNumberOfRanges(ranges);

        long endTime = System.nanoTime();
        long timeElapsed = endTime - startTime;

        System.out.println("Execution time in milliseconds : " + timeElapsed / 1000000);
        Assert.assertNotNull(timeElapsed);
    }

    @Test
    public void testGetUinqueMiniumNumberOfRangesWithOneMillionZipCodeRange() throws InterruptedException, IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("zip-code-range-one-million.txt").getFile());
        String content = new String(Files.readAllBytes(file.toPath()));
        List ranges = new ArrayList();
        Scanner input = new Scanner(content);
        while (input.hasNext()) {
            ranges.add(input.next());
        }

        long startTime = System.nanoTime();

        zipCodeRangeService.getUinqueMiniumNumberOfRanges(ranges);

        long endTime = System.nanoTime();
        long timeElapsed = endTime - startTime;

        System.out.println("Execution time in milliseconds : " + timeElapsed / 1000000);
        Assert.assertNotNull(timeElapsed);
    }

    private String actualZipCodeString(List<ZipCodeRange> ranges) {
        return ranges.stream().map(zipCodeRange -> zipCodeRange.toString()).collect(Collectors.joining(" "));
    }

    private String expectedZipCodeString(List<String> ranges) {
        return (String) ranges.stream().collect(Collectors.joining(" "));
    }
}
