package org.babyshark.chagok.domain.asset.api;

import lombok.RequiredArgsConstructor;
import org.babyshark.chagok.domain.asset.application.AssetService;
import org.babyshark.chagok.domain.asset.domain.Asset;
import org.babyshark.chagok.domain.asset.dto.AssetRequest;
import org.babyshark.chagok.domain.asset.dto.AssetResponse;
import org.babyshark.chagok.global.auth.TokenService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/asset")
public class AssetConroller {

  private final AssetService assetService;
  private final TokenService tokenService;

  @PostMapping
  public ResponseEntity<String> createAsset(@RequestHeader("Authorization") String token,
      @RequestBody AssetRequest request) {

    var memberId = tokenService.getMemberId(token);
    assetService.createAsset(memberId, request);

    return ResponseEntity.ok("ok");
  }

  @GetMapping("/list")
  public ResponseEntity<AssetResponse> listAssets(@RequestHeader("Authorization") String token) {

    var memberId = tokenService.getMemberId(token);
    assetService.getAssetsByUserId(memberId);

    return ResponseEntity.ok("ok");
  }

}
