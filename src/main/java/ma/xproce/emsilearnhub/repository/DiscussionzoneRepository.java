package ma.xproce.emsilearnhub.repository;

import ma.xproce.emsilearnhub.model.Discussionzone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DiscussionzoneRepository extends JpaRepository<Discussionzone, Long> {
    Optional<Discussionzone> findByName(String discussionzoneName);
}