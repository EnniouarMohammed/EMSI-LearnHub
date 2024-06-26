package ma.xproce.emsilearnhub.mapper;

import com.github.marlonlom.utilities.timeago.TimeAgo;
import ma.xproce.emsilearnhub.dto.PostRequest;
import ma.xproce.emsilearnhub.dto.PostResponse;
import ma.xproce.emsilearnhub.model.*;
import ma.xproce.emsilearnhub.repository.CommentRepository;
import ma.xproce.emsilearnhub.repository.VoteRepository;
import ma.xproce.emsilearnhub.service.AuthService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static ma.xproce.emsilearnhub.model.VoteType.DOWNVOTE;
import static ma.xproce.emsilearnhub.model.VoteType.UPVOTE;

@Mapper(componentModel = "spring")
public abstract class PostMapper {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private VoteRepository voteRepository;
    @Autowired
    private AuthService authService;


    @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
    @Mapping(target = "description", source = "postRequest.description")
    @Mapping(target = "discussionzone", source = "discussionzone")
    @Mapping(target = "voteCount", constant = "0")
    @Mapping(target = "user", source = "user")
    public abstract Post map(PostRequest postRequest, Discussionzone discussionzone, User user);

    @Mapping(target = "id", source = "postId")
    @Mapping(target = "discussionzoneName", source = "discussionzone.name")
    @Mapping(target = "userName", source = "user.username")
    @Mapping(target = "commentCount", expression = "java(commentCount(post))")
    @Mapping(target = "duration", expression = "java(getDuration(post))")
    @Mapping(target = "upVote", expression = "java(isPostUpVoted(post))")
    @Mapping(target = "downVote", expression = "java(isPostDownVoted(post))")
    public abstract PostResponse mapToDto(Post post);

    Integer commentCount(Post post) { return commentRepository.findByPost(post).size(); }

    String getDuration(Post post) { return TimeAgo.using(post.getCreatedDate().toEpochMilli()); }

    boolean isPostUpVoted(Post post) { return checkVoteType(post, UPVOTE); }

    boolean isPostDownVoted(Post post) {
        return checkVoteType(post, DOWNVOTE);
    }

    private boolean checkVoteType(Post post, VoteType voteType) {
        if (authService.isLoggedIn()) {
            Optional<Vote> voteForPostByUser =
                    voteRepository.findTopByPostAndUserOrderByVoteIdDesc(post,
                            authService.getCurrentUser());
            return voteForPostByUser.filter(vote -> vote.getVoteType().equals(voteType))
                    .isPresent();
        }
        return false;
    }
}