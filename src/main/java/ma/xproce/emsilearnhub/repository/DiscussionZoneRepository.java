package ma.xproce.emsilearnhub.repository;

import ma.xproce.emsilearnhub.model.DiscussionZone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DiscussionZoneRepository extends JpaRepository<DiscussionZone, Long> {
    Optional<DiscussionZone> findByName(String discussionzoneName);
}