package io.vukotic.imdb.movierater.service;

import com.google.gson.Gson;
import io.vukotic.imdb.movierater.model.dto.ReleaseDto;
import io.vukotic.imdb.movierater.model.mapper.ReleaseMapper;
import io.vukotic.imdb.movierater.repository.ReleaseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
@Slf4j
public class DataService {
    private final ReleaseRepository releaseRepository;

    @Value("${dataset.movie}")
    private String movieDatasetPath;
    @Value("${dataset.tv}")
    private String tvDatasetPath;

    @PostConstruct
    public void buildIndex() {
        ReleaseMapper releaseMapper = new ReleaseMapper();
        var movieReleases = prepareDataset(movieDatasetPath).stream().map(releaseMapper::convertReleaseToEntity).collect(Collectors.toList());
        var tvReleases = prepareDataset(tvDatasetPath).stream().map(releaseMapper::convertReleaseToEntity).collect(Collectors.toList());
        releaseRepository.saveAll(movieReleases);
        releaseRepository.saveAll(tvReleases);
        var allReleases = releaseRepository.findAll();
    }

    private List<ReleaseDto> prepareDataset(String datasetPath) {

        Gson gson = new Gson();
        try {
            var inputStream = new ClassPathResource(datasetPath).getInputStream();
            var jsonString = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
            var releaseArray = gson.fromJson(jsonString, ReleaseDto[].class);
            return new ArrayList<>(Arrays.asList(releaseArray));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
}
