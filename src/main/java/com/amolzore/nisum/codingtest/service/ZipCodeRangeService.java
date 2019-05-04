package com.amolzore.nisum.codingtest.service;

import com.amolzore.nisum.codingtest.dataaccess.model.ZipCodeRange;
import com.amolzore.nisum.codingtest.dataaccess.utils.ZipCodeRangeUtils;
import lombok.NonNull;

import java.util.Comparator;
import java.util.List;

/**
 * Given a collection of 5-digit ZIP code ranges (each range includes both their upper and lower bounds),
 * provide an algorithm that produces the minimum number of ranges required to represent the same restrictions as the input.
 */
public class ZipCodeRangeService {

    @NonNull
    /**
     * 1. Parse the input zip code ranges (String of [XXXXX,XXXXX] pattern) and collect the list of zip code ranges with object type as ZipCodeRange.
     * 2. Sort that list of ZipCodeRanges object based on lower limit.
     * 3. Loop over list of ZipCodeRanges
     * 3.1 Collect current and next ZipCodeRange
     * 3.2 Check if both current and next ZipCodeRange are samme, if same then need to rid of second ZipCodeRange form list and
     *     set the pointer of current next to ZipCodeRange of list.
     * 3.3 If current and next ZipCodeRange are not same then check consolidation is required or not, is consolidation required
     *     then
     *      3.3.1 Find the new upper limit for current ZipCodeRange using getNewUpperLimit from ZipCodeRangeUtils utility and set on that object.
     *      3.3.2 Rid of the next ZipCodeRange from List and marke that consolidation happened.
     *      3.3.3 If consolidation happened then ste the current index to current one using nextIdx - 1 and do not increment nextIdx.
     *      3.3.4 If condolidation did not happened then and current and next ZipCodeRange are not same then no operation required on ZipCodeRange
     *            only increment the current and next index by one.
     */
    public List<ZipCodeRange> getUinqueMinimumNumberOfRanges(List<String> ranges) {
        List<ZipCodeRange> zipCodeRanges = ZipCodeRangeUtils.parseZipCodeRange(ranges);
        zipCodeRanges.sort(Comparator.comparing(ZipCodeRange::getLowerLimit));
        int currentIdx = 0;
        int nextIdx = 1;
        boolean isModified;
        while (  currentIdx+1 < zipCodeRanges.size() && zipCodeRanges.size() != 1 ) {
            isModified = false;
            ZipCodeRange currentZipCodeRange = zipCodeRanges.get(currentIdx);
            ZipCodeRange nextZipCodeRange = zipCodeRanges.get(nextIdx);
            if (!(ZipCodeRangeUtils.isIgnore(currentZipCodeRange,nextZipCodeRange))) {
                if (ZipCodeRangeUtils.isConsolidate(currentZipCodeRange,nextZipCodeRange)) {
                    currentZipCodeRange.setUpperLimit(ZipCodeRangeUtils.getNewUpperLimit(currentZipCodeRange,nextZipCodeRange));
                    zipCodeRanges.remove(nextIdx);
                    isModified = true;
                }
            } else {
                zipCodeRanges.remove(nextIdx);
                isModified = true;
            }

            if(isModified) {
                currentIdx = nextIdx -1;
            } else {
                currentIdx++;
                nextIdx++;
            }
        }
        return zipCodeRanges;
    }
}
