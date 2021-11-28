package io.vukotic.imdb.movierater.util;

import io.vukotic.imdb.movierater.model.dto.PatternMatchDto;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NLPatternsUtil {
    public static PatternMatchDto checkForNlPatterns(String searchQuery) {
        var match = nlMatch(NLPatterns.AT_LEAST_STAR_NUMBER, searchQuery);
        if (match != null) {
            return match;
        }
        match = nlMatch(NLPatterns.STAR_NUMBER, searchQuery);
        if (match != null) {
            return match;
        }
        match = nlMatch(NLPatterns.AFTER_YEAR, searchQuery);
        if (match != null) {
            return match;
        }
        match = nlMatch(NLPatterns.BEFORE_YEAR, searchQuery);
        if (match != null) {
            return match;
        }
        match = nlMatch(NLPatterns.OLDER_THAN_YEARS, searchQuery);
        if (match != null) {
            return match;
        }
        return new PatternMatchDto(NLPatterns.DEFAULT, 0);
    }

    public static PatternMatchDto nlMatch(NLPatterns patternType, String searchQuery) {
        String pattern = patternType.pattern;
        Pattern r = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);
        Matcher m = r.matcher(searchQuery);
        if (m.find()) {
            return new PatternMatchDto(patternType, Integer.parseInt(m.group(1)));
        }
        return null;
    }
}
