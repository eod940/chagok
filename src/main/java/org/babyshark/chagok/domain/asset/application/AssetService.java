package org.babyshark.chagok.domain.asset.application;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.babyshark.chagok.domain.asset.domain.Asset;
import org.babyshark.chagok.domain.asset.domain.AssetCategory;
import org.babyshark.chagok.domain.asset.dto.AssetRequest;
import org.babyshark.chagok.domain.asset.repository.AssetCategoryRepository;
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
  private final AssetCategoryRepository assetCategoryRepository;

  @Transactional
  public void createAsset(long userId, AssetRequest assetRequest) {
    Member user = memberRepository.findById(userId)
        .orElseThrow(() -> new RuntimeException("User not found"));
    List<AssetCategory> assetCategories = assetRequest.assetCategoryNames().stream()
        .map(assetCategoryRepository::findAssetCategoryByName)
        .flatMap(List::stream)
        .toList();

    assetRepository.save(Asset.create(assetRequest, assetCategories));
  }

  public void getAssetsByUserId(Long userId) {
    Member user = memberRepository.findById(userId)
        .orElseThrow(() -> new RuntimeException("User not found"));

  }
}
