package ma.xproce.emsilearnhub.service;

import lombok.AllArgsConstructor;
import ma.xproce.emsilearnhub.dto.CommentsDto;
import ma.xproce.emsilearnhub.exceptions.PostNotFoundException;
import ma.xproce.emsilearnhub.exceptions.SpringException;
import ma.xproce.emsilearnhub.mapper.CommentMapper;
import ma.xproce.emsilearnhub.model.Comment;
import ma.xproce.emsilearnhub.model.NotificationEmail;
import ma.xproce.emsilearnhub.model.Post;
import ma.xproce.emsilearnhub.model.User;
import ma.xproce.emsilearnhub.repository.CommentRepository;
import ma.xproce.emsilearnhub.repository.PostRepository;
import ma.xproce.emsilearnhub.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CommentService {
    private static final String POST_URL = "";
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final AuthService authService;
    private final CommentMapper commentMapper;
    private final CommentRepository commentRepository;
    private final MailContentBuilder mailContentBuilder;
    private final MailService mailService;

    public void save(CommentsDto commentsDto) {
        Post post = postRepository.findById(commentsDto.getPostId())
                .orElseThrow(() -> new PostNotFoundException(commentsDto.getPostId().toString()));
        Comment comment = commentMapper.map(commentsDto, post, authService.getCurrentUser());
        commentRepository.save(comment);

        String message = mailContentBuilder.build(post.getUser().getUsername() + " posted a comment on your post." + POST_URL);
        sendCommentNotification(message, post.getUser());
    }

    private void sendCommentNotification(String message, User user) {
        mailService.sendMail(new NotificationEmail(user.getUsername() + " Commented on your post", user.getEmail(), message));
    }

    public List<CommentsDto> getAllCommentsForPost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException(postId.toString()));
        return commentRepository.findByPost(post)
                .stream()
                .map(commentMapper::mapToDto).toList();
    }

    public List<CommentsDto> getAllCommentsForUser(String userName) {
        User user = userRepository.findByUsername(userName)
                .orElseThrow(() -> new UsernameNotFoundException(userName));
        return commentRepository.findAllByUser(user)
                .stream()
                .map(commentMapper::mapToDto)
                .toList();
    }
}