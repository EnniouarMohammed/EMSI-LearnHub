package ma.xproce.emsilearnhub.service;

import ma.xproce.emsilearnhub.dto.DiscussionzoneDto;
import ma.xproce.emsilearnhub.exceptions.SpringException;
import ma.xproce.emsilearnhub.mapper.DiscussionzoneMapper;
import ma.xproce.emsilearnhub.model.Discussionzone;
import ma.xproce.emsilearnhub.repository.DiscussionzoneRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
@Slf4j
public class DiscussionzoneService {
    private final DiscussionzoneRepository discussionzoneRepository;
    private final DiscussionzoneMapper discussionzoneMapper;

    @Transactional
    public DiscussionzoneDto save(DiscussionzoneDto discussionzoneDto) {
        Discussionzone save = discussionzoneRepository.save(discussionzoneMapper.mapDtoToDiscussionzone(discussionzoneDto));
        discussionzoneDto.setId(save.getId());
        return discussionzoneDto;
    }

    @Transactional(readOnly = true)
    public List<DiscussionzoneDto> getAll() {
        return discussionzoneRepository.findAll()
                .stream()
                .map(discussionzoneMapper::mapDiscussionzoneToDto)
                .collect(toList());
    }

    public DiscussionzoneDto getDiscussionzone(Long id) {
        Discussionzone discussionzone = discussionzoneRepository.findById(id)
                .orElseThrow(() -> new SpringException("No Discussion Zone found with ID - " + id));
        return discussionzoneMapper.mapDiscussionzoneToDto(discussionzone);
    }
}