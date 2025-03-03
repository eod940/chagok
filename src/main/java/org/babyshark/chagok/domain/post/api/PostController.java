package org.babyshark.chagok.domain.post.api;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.babyshark.chagok.domain.post.domain.Post;
import org.babyshark.chagok.domain.post.repository.PostRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {

  private final PostRepository postRepository;

  @GetMapping
  public ResponseEntity<List<Post>> getAllPost() {
    return ResponseEntity.ok(postRepository.findAll());
  }

  @GetMapping("/{postId}")
  public ResponseEntity<Post> getPost(@PathVariable(name = "postId") long postId) {
    return ResponseEntity.ok(postRepository.findById(postId).orElse(null));
  }
}
