package ma.xproce.emsilearnhub.repository;

import ma.xproce.emsilearnhub.model.Post;
import ma.xproce.emsilearnhub.model.User;
import ma.xproce.emsilearnhub.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {
    Optional<Vote> findTopByPostAndUserOrderByVoteIdDesc(Post post, User currentUser);
}
