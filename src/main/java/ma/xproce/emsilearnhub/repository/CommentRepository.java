package ma.xproce.emsilearnhub.repository;


import ma.xproce.emsilearnhub.model.Comment;
import ma.xproce.emsilearnhub.model.Post;
import ma.xproce.emsilearnhub.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPost(Post post);

    List<Comment> findAllByUser(User user);
}
