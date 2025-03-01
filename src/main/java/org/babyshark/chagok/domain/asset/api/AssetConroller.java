package org.babyshark.chagok.domain.asset.api;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.babyshark.chagok.domain.asset.application.AssetService;
import org.babyshark.chagok.domain.asset.domain.Asset;
import org.babyshark.chagok.domain.asset.dto.AssetAssistResponse;
import org.babyshark.chagok.domain.asset.dto.AssetRequest;
import org.babyshark.chagok.domain.asset.dto.AssetResponse;
import org.babyshark.chagok.global.auth.TokenService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/asset")
public class AssetConroller {

  private final AssetService assetService;
  private final TokenService tokenService;

  @PostMapping
  public ResponseEntity<AssetAssistResponse> createAsset(@RequestHeader("Authorization") String token,
      @RequestBody AssetRequest request) {

    var memberId = tokenService.getMemberId(token);

    return ResponseEntity.ok(assetService.createAsset(memberId, request));
  }

  @GetMapping("/list")
  public ResponseEntity<List<AssetResponse>> listAssets(@RequestHeader("Authorization") String token) {

    var memberId = tokenService.getMemberId(token);

    return ResponseEntity.ok(assetService.getAssetsByUserId(memberId));
  }

  @DeleteMapping("/{assetId}")
  public ResponseEntity<Void> deleteAsset(@RequestHeader("Authorization") String token,
      @PathVariable(name = "assetId") long assetId) {

    var memberId = tokenService.getMemberId(token);
    assetService.deleteAsset(memberId, assetId);

    return ResponseEntity.noContent().build();
  }
}
