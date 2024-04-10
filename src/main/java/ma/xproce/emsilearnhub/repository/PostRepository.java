package ma.xproce.emsilearnhub.repository;

import ma.xproce.emsilearnhub.model.Discussionzone;
import ma.xproce.emsilearnhub.model.Post;
import ma.xproce.emsilearnhub.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByDiscussionzone(Discussionzone discussionzone);

    List<Post> findByUser(User user);
}
