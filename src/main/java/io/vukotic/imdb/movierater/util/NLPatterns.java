package io.vukotic.imdb.movierater.util;

public enum NLPatterns {
    STAR_NUMBER("([1-5]) star"),
    AT_LEAST_STAR_NUMBER("at least ([1-5]) star"),
    AFTER_YEAR("after (\\d\\d\\d\\d)"),
    BEFORE_YEAR("before (\\d\\d\\d\\d)"),
    OLDER_THAN_YEARS("older than (\\d) year"),
    DEFAULT("");

    public final String pattern;

    private NLPatterns(String pattern) {
        this.pattern = pattern;
    }
}
