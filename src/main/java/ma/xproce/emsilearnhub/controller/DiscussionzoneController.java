package ma.xproce.emsilearnhub.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.xproce.emsilearnhub.dto.DiscussionzoneDto;
import ma.xproce.emsilearnhub.service.DiscussionzoneService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/discussionzone")
@AllArgsConstructor
@Slf4j
public class DiscussionzoneController {
    private final DiscussionzoneService discussionzoneService;

    @PostMapping
    public ResponseEntity<DiscussionzoneDto> createDiscussionzone(@RequestBody DiscussionzoneDto discussionzoneDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(discussionzoneService.save(discussionzoneDto));
    }

    @GetMapping
    public ResponseEntity<List<DiscussionzoneDto>> getAllDiscussionzones() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(discussionzoneService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DiscussionzoneDto> getDiscussionzone(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(discussionzoneService.getDiscussionzone(id));
    }
}
