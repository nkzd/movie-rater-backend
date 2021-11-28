package io.vukotic.imdb.movierater.controller;

import io.vukotic.imdb.movierater.model.dto.RatingDto;
import io.vukotic.imdb.movierater.model.dto.ReleaseDto;
import io.vukotic.imdb.movierater.model.entity.ReleaseType;
import io.vukotic.imdb.movierater.model.mapper.ReleaseMapper;
import io.vukotic.imdb.movierater.service.ReleaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping(value = "/releases")
public class ReleaseController {
    private final ReleaseService releaseService;
    private final ReleaseMapper releaseMapper;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<ReleaseDto> searchReleases(
            @RequestParam(required = false, defaultValue = "movie") ReleaseType type,
            @RequestParam(required = false, defaultValue = "") String searchQuery,
            @RequestParam(required = false, defaultValue = "0") int page
    ) {
        var releases = (type.equals(ReleaseType.all))
                ? releaseService.getAllReleases()
                : releaseService.searchReleases(type, searchQuery, page);
        return releases.stream().map(releaseMapper::convertReleaseToDto).collect(Collectors.toList());
    }


    @PostMapping("/{releaseId}/rate")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ReleaseDto rateRelease(
            @PathVariable String releaseId, @Valid @RequestBody RatingDto ratingDto
    ) {
        var updatedRelease = releaseService.rateRelease(releaseId, ratingDto.getRating());
        return releaseMapper.convertReleaseToDto(updatedRelease);
    }

}
