package io.vukotic.imdb.movierater.repository;

import io.vukotic.imdb.movierater.model.entity.Release;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface ReleaseRepository extends ElasticsearchRepository<Release, String> {

    @Query("{\"bool\":{\"must\":{\"match_all\":{}},\"filter\":[{\"term\":{\"type\":\"?0\"}},{\"bool\":{\"should\":[{\"match\":{\"actors\":\"?1\"}},{\"match\":{\"title\":\"?1\"}},{\"match\":{\"description\":\"?1\"}}]}}]}}")
    Page<Release> searchReleasesByType(String type, String searchQuery, Pageable pageable);

    Page<Release> findReleasesByTypeEquals(String type, Pageable pageable);

    //at least _ stars
    Page<Release> findReleasesByTypeEqualsAndAverageRatingGreaterThanEqual(String type, Double averageRating, Pageable pageable);

    // _ stars
//    Page<Release> findReleasesByTypeEqualsAndAverageRatingBetween(String type, Double averageRating, Double averageRating2, Pageable pageable);

    Page<Release> findReleasesByTypeEqualsAndAverageRatingGreaterThanEqualAndAverageRatingLessThanEqual(String type, Double averageRating, Double averageRating2, Pageable pageable);
    // after _
    Page<Release> findReleasesByTypeEqualsAndYearGreaterThanEqual(String type, Integer year, Pageable pageable);

    // before _ || older than _ years
    Page<Release> findReleasesByTypeEqualsAndYearLessThanEqual(String type, Integer year, Pageable pageable);

    List<Release> findAll();
}


