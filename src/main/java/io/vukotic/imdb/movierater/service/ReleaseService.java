package io.vukotic.imdb.movierater.service;

import io.vukotic.imdb.movierater.error.ReleaseNotFoundException;
import io.vukotic.imdb.movierater.model.entity.Release;
import io.vukotic.imdb.movierater.model.entity.ReleaseType;
import io.vukotic.imdb.movierater.repository.ReleaseRepository;
import io.vukotic.imdb.movierater.util.NLPatterns;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

import static io.vukotic.imdb.movierater.util.NLPatternsUtil.checkForNlPatterns;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReleaseService {
    private final ReleaseRepository releaseRepository;

    public List<Release> searchReleases(ReleaseType type, String searchQuery, int page) {
        Pageable pageable = PageRequest.of(page, 10, Sort.by("averageRating").descending());

        var patternMatchDto = checkForNlPatterns(searchQuery);

        switch (patternMatchDto.getType()) {
            case STAR_NUMBER: {
                var averageRating = patternMatchDto.getValue() * 2.0;
                var averageRatingLower = averageRating - 0.9;
                var averageRatingUpper = averageRating + 0.9;
                return releaseRepository.findReleasesByTypeEqualsAndAverageRatingGreaterThanEqualAndAverageRatingLessThanEqual(type.toString(), averageRatingLower, averageRatingUpper, pageable).getContent();
            }
            case AT_LEAST_STAR_NUMBER: {
                var averageRating = patternMatchDto.getValue() * 2.0 - 0.9;
                return releaseRepository.findReleasesByTypeEqualsAndAverageRatingGreaterThanEqual(type.toString(), averageRating, pageable).getContent();
            }
            case AFTER_YEAR: {
                var year = patternMatchDto.getValue();
                return releaseRepository.findReleasesByTypeEqualsAndYearGreaterThanEqual(type.toString(), year, pageable).getContent();
            }
            case BEFORE_YEAR: {
                var year = patternMatchDto.getValue();
                return releaseRepository.findReleasesByTypeEqualsAndYearLessThanEqual(type.toString(), year, pageable).getContent();
            }
            case OLDER_THAN_YEARS: {
                var currentYear = ZonedDateTime.now(ZoneId.of("Europe/Sarajevo"))
                        .getYear();
                var yearsBefore = currentYear - patternMatchDto.getValue();
                return releaseRepository.findReleasesByTypeEqualsAndYearLessThanEqual(type.toString(), yearsBefore, pageable).getContent();
            }
            default: {
                return searchQuery.isBlank() ? releaseRepository.findReleasesByTypeEquals(type.toString(), pageable).getContent() :
                        releaseRepository.searchReleasesByType(type.toString(), searchQuery, pageable).getContent();
            }

        }

    }


    public List<Release> getAllReleases() {
        return releaseRepository.findAll();
    }

    public Release rateRelease(String id, Double rating) {
        var release = releaseRepository.findById(id).orElseThrow(ReleaseNotFoundException::new);
        Integer oldNumberOfRatings = release.getNumberOfRatings();
        Double oldAverageRating = release.getAverageRating();
        Integer newNumberOfRatings = oldNumberOfRatings + 1;
        release.setNumberOfRatings(release.getNumberOfRatings() + 1);
        Double newRating = (oldNumberOfRatings * oldAverageRating + rating) / newNumberOfRatings;
        release.setAverageRating(newRating);
        releaseRepository.save(release);
        return release;
    }


}
