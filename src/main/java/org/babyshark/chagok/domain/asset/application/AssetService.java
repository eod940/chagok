package org.babyshark.chagok.domain.asset.application;

import lombok.RequiredArgsConstructor;
import org.babyshark.chagok.domain.asset.domain.Asset;
import org.babyshark.chagok.domain.asset.dto.AssetRequest;
import org.babyshark.chagok.domain.asset.repository.AssetRepository;
import org.babyshark.chagok.domain.member.domain.Member;
import org.babyshark.chagok.domain.member.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AssetService {

  private final MemberRepository memberRepository;
  private final AssetRepository assetRepository;

  @Transactional
  public void createAsset(long userId, AssetRequest assetRequest) {
    Member user = memberRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
    assetRepository.save(Asset.create(assetRequest));
  }

  public void getAssetsByUserId(Long userId) {
    Member user = memberRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

  }
}
