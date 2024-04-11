package ma.xproce.emsilearnhub.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.xproce.emsilearnhub.dto.PostRequest;
import ma.xproce.emsilearnhub.dto.PostResponse;
import ma.xproce.emsilearnhub.exceptions.DiscussionzoneNotFoundException;
import ma.xproce.emsilearnhub.exceptions.PostNotFoundException;
import ma.xproce.emsilearnhub.mapper.PostMapper;
import ma.xproce.emsilearnhub.model.*;
import ma.xproce.emsilearnhub.repository.DiscussionzoneRepository;
import ma.xproce.emsilearnhub.repository.PostRepository;
import ma.xproce.emsilearnhub.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class PostService {

    private final PostRepository postRepository;
    private final DiscussionzoneRepository discussionzoneRepository;
    private final UserRepository userRepository;
    private final AuthService authService;
    private final PostMapper postMapper;

    public void save(PostRequest postRequest) {
        Discussionzone discussionzone = discussionzoneRepository.findByName(postRequest.getDiscussionzoneName())
                .orElseThrow(() -> new DiscussionzoneNotFoundException(postRequest.getDiscussionzoneName()));
        postRepository.save(postMapper.map(postRequest, discussionzone, authService.getCurrentUser()));
    }

    @Transactional(readOnly = true)
    public PostResponse getPost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException(id.toString()));
        return postMapper.mapToDto(post);
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getAllPosts() {
        return postRepository.findAll()
                .stream()
                .map(postMapper::mapToDto)
                .collect(toList());
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getPostsByDiscussionzone(Long discussionzoneId) {
        Discussionzone discussionzone = discussionzoneRepository.findById(discussionzoneId)
                .orElseThrow(() -> new DiscussionzoneNotFoundException(discussionzoneId.toString()));
        List<Post> posts = postRepository.findAllByDiscussionzone(discussionzone);
        return posts.stream().map(postMapper::mapToDto).collect(toList());
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getPostsByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
        return postRepository.findByUser(user)
                .stream()
                .map(postMapper::mapToDto)
                .collect(toList());
    }
}