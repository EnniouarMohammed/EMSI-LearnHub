package ma.xproce.emsilearnhub.controller;

import lombok.AllArgsConstructor;
import ma.xproce.emsilearnhub.dto.PostRequest;
import ma.xproce.emsilearnhub.dto.PostResponse;
import ma.xproce.emsilearnhub.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/api/posts")
@AllArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<Void> createPost(@RequestBody PostRequest postRequest) {
        postService.save(postRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<PostResponse>> getAllPosts() {
        return status(HttpStatus.OK).body(postService.getAllPosts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getPost(@PathVariable Long id) {
        return status(HttpStatus.OK).body(postService.getPost(id));
    }

    @GetMapping(params = "discussionzoneId")
    public ResponseEntity<List<PostResponse>> getPostsByDiscussionzone(@RequestParam Long discussionzoneId) {
        return status(HttpStatus.OK).body(postService.getPostsByDiscussionzone(discussionzoneId));
    }

    @GetMapping(params = "username")
    public ResponseEntity<List<PostResponse>> getPostsByUsername(@RequestParam String username) {
        return status(HttpStatus.OK).body(postService.getPostsByUsername(username));
    }
}