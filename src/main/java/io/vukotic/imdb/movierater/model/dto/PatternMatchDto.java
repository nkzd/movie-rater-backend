package io.vukotic.imdb.movierater.model.dto;

import io.vukotic.imdb.movierater.util.NLPatterns;
import lombok.Data;

@Data
public class PatternMatchDto {
    private final NLPatterns type;
    private final Integer value;
}
