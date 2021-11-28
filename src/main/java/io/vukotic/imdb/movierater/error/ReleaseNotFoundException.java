package io.vukotic.imdb.movierater.error;

public class ReleaseNotFoundException extends RuntimeException {
    public ReleaseNotFoundException() {
        super("Release not found");
    }
}
