package ma.xproce.emsilearnhub.mapper;

import ma.xproce.emsilearnhub.dto.DiscussionzoneDto;
import ma.xproce.emsilearnhub.model.Discussionzone;
import ma.xproce.emsilearnhub.model.Post;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DiscussionzoneMapper {

    @Mapping(target = "numberOfPosts", expression = "java(mapPosts(discussionzone.getPosts()))")
    DiscussionzoneDto mapDiscussionzoneToDto(Discussionzone discussionzone);

    default Integer mapPosts(List<Post> numberOfPosts) {
        return numberOfPosts.size();
    }

    @InheritInverseConfiguration
    @Mapping(target = "posts", ignore = true)
    Discussionzone mapDtoToDiscussionzone(DiscussionzoneDto discussionzoneDto);
}
