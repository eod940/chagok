package org.babyshark.chagok.domain.asset.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/asset")
public class AssetConroller {

  @GetMapping
  public ResponseEntity<String> createAsset() {
    return ResponseEntity.ok("ok");
  }

}
