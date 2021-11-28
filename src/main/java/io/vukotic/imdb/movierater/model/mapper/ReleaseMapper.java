package io.vukotic.imdb.movierater.model.mapper;


import io.vukotic.imdb.movierater.model.dto.ReleaseDto;
import io.vukotic.imdb.movierater.model.entity.Release;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ReleaseMapper {
    private final ModelMapper modelMapper = new ModelMapper();

    public Release convertReleaseToEntity(ReleaseDto release) {
        return modelMapper.map(release, Release.class);
    }

    public ReleaseDto convertReleaseToDto(Release release) {
        return modelMapper.map(release, ReleaseDto.class);
    }
}
