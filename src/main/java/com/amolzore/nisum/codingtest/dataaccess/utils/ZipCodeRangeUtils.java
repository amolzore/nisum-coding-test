package com.amolzore.nisum.codingtest.dataaccess.utils;

import com.amolzore.nisum.codingtest.dataaccess.model.ZipCodeRange;

import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;
import lombok.NonNull;

import static java.util.stream.Collectors.toList;

public class ZipCodeRangeUtils {
    /**
     * parse the input list of zip code range (string) one by one using stream
     * 1. Validate the zip code range with their length and as pattern i.e length should be equal to 5 and pattern should be like [XXXXX,XXXXX].
     * 2. May need swapping of ranges as its not thumb rule that zip code ranges are provided like this [LOWER,UPPER].
     *    We may get zip code ranges as [UPPER,LOWER] so swap will require.
     * 3. Create zip code range object and add them to List of ZipCodeRanges.
     * @param zipCodeRanges
     * @return List<ZipCodeRange> zipCodeRanges;
     */
    @NonNull
    public static List<ZipCodeRange> parseZipCodeRange(final List<String> zipCodeRanges) {
        final List<ZipCodeRange> collect = zipCodeRanges.stream()
                .filter(Objects::nonNull)
                .filter(zipCodeRange-> isValidZipCodeRange(zipCodeRange))
                .map(zipCodeRange -> {
                    final String range[] = zipCodeRange.replace("[", "").replace("]", "").split(",");
                    int lowerLimit = Integer.parseInt(range[0]);
                    int upperLimit = Integer.parseInt(range[1]);

                    if (upperLimit < lowerLimit) {
                        upperLimit = upperLimit + lowerLimit;
                        lowerLimit = upperLimit - lowerLimit;
                        upperLimit = upperLimit - lowerLimit;
                    }
                    return new ZipCodeRange(lowerLimit, upperLimit);
                }).collect(toList());
        return collect;
    }

    /**
     * Validate the zip code range with their length and as pattern i.e length should be equal to 5 and pattern should be like [XXXXX,XXXXX].
     * @param zipCodeRange
     * @return boolean [true|false]
     */
    @NonNull
    public static boolean isValidZipCodeRange(final String zipCodeRange) {
        return ((Pattern.compile("\\[\\d{5},\\d{5}\\]")).matcher(zipCodeRange)).matches();
    }

    /**
     * Find the upper limit based on (c.UpperLimit > n.upperLimit) from c.upperLimit else n.upperLimit
     * @param currentZipCodeRange
     * @param nextZipCodeRange
     * @return upperLimit int
     */
    @NonNull
    public static int getNewUpperLimit(final ZipCodeRange currentZipCodeRange, final ZipCodeRange nextZipCodeRange) {
        int upperLimit = 0;
        if (currentZipCodeRange.getUpperLimit() > nextZipCodeRange.getUpperLimit()) {
            upperLimit = currentZipCodeRange.getUpperLimit();
        } else {
            upperLimit = nextZipCodeRange.getUpperLimit();
        }
        return upperLimit;
    }

    /**
     * if the two zip codes are same then skip them from consolidation.
     * @param currentZipCodeRange
     * @param nextZipCodeRange
     * @return boolean [true|false]
     */
    @NonNull
    public static boolean isIgnore(final ZipCodeRange currentZipCodeRange, final ZipCodeRange nextZipCodeRange) {
        if (!currentZipCodeRange.equals(nextZipCodeRange)) {
            return false;
        }
        return true;
    }

    /**
     * Check if condolidation will require. if the c.upperLimit >= n.lowerLimit then consolidation is must.
     * @param currentZipCodeRange
     * @param nextZipCodeRange
     * @return boolean [true|false]
     */
    @NonNull
    public static boolean isConsolidate(final ZipCodeRange currentZipCodeRange, final ZipCodeRange nextZipCodeRange) {
        if (currentZipCodeRange.getUpperLimit() >= nextZipCodeRange.getLowerLimit()) {
            return true;
        }
        return false;
    }
}