package org.babyshark.chagok.domain.asset.application;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.babyshark.chagok.domain.asset.domain.Asset;
import org.babyshark.chagok.domain.asset.domain.AssetCategory;
import org.babyshark.chagok.domain.asset.dto.AssetAssistResponse;
import org.babyshark.chagok.domain.asset.dto.AssetRequest;
import org.babyshark.chagok.domain.asset.dto.AssetResponse;
import org.babyshark.chagok.domain.asset.repository.AssetCategoryRepository;
import org.babyshark.chagok.domain.asset.repository.AssetRepository;
import org.babyshark.chagok.domain.member.domain.Member;
import org.babyshark.chagok.domain.member.repository.MemberRepository;
import org.babyshark.chagok.global.error.CustomException;
import org.babyshark.chagok.global.model.ErrorCode;
import static org.babyshark.chagok.global.model.ErrorCode.PAGE_NOT_FOUND;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AssetService {

  private final MemberRepository memberRepository;
  private final AssetRepository assetRepository;
  private final AssetCategoryRepository assetCategoryRepository;

  @Transactional
  public AssetAssistResponse createAsset(long userId, AssetRequest assetRequest) {
    Member user = memberRepository.findById(userId)
        .orElseThrow(() -> new RuntimeException("User not found"));
    List<AssetCategory> assetCategories = assetRequest.assetCategoryNames().stream()
        .map(assetCategoryRepository::findAssetCategoryByName)
        .flatMap(List::stream)
        .toList();

    Asset createdAsset = Asset.create(assetRequest, assetCategories, user);
    AssetAssistResponse response = new AssetAssistResponse(assembleAssetRequest(createdAsset));
    createdAsset.setAssistQuery(response.message());
    assetRepository.save(createdAsset);

    return response;
  }

  public List<AssetResponse> getAssetsByUserId(Long userId) {

    return assetRepository.findAllByMember_MemberId(userId).stream()
        .map(AssetResponse::create)
        .toList();
  }

  private String assembleAssetRequest(Asset assetRequest) {

    String categoriesString = assetRequest.getAssetCategories().stream()
        .map(AssetCategory::getName)
        .collect(Collectors.joining(", "));
    String itemName = "없고, 무소유를 실천중";
    String itemPrice = "없어. 다행";

    if (categoriesString.isEmpty()) {
      categoriesString = "없고, 무소유를 실천중";
    }

    if (assetRequest.getItemName() != null && !assetRequest.getItemName().isEmpty()) {
      itemName = assetRequest.getItemName();
      itemPrice = assetRequest.getItemPrice().toString() + "원";
    }


    String userMessage = "";
    userMessage += "한 달 용돈이 " + assetRequest.getMonthlyAllowance().toString() + "이야.\n";
    userMessage += "필수 지출은 " + categoriesString + "이야.\n";
    userMessage += "갖고 싶은 건 " + itemName + "이야.\n";
    userMessage += "가격은 " + itemPrice + "이야.\n";
    userMessage += "저축하고 싶은 금액은 " + assetRequest.getSavings() + "원 이야.";

    return userMessage;
  }

  @Transactional
  public void deleteAsset(long memberId, long assetId) {

    log.info("AssetService deleteAsset: memberId: {}, assetId: {}", memberId, assetId);
    Asset asset = assetRepository.findById(assetId)
        .orElseThrow(() -> new CustomException(PAGE_NOT_FOUND));// 404

    if (!Objects.equals(asset.getMember().getMemberId(), memberId)) {
      throw new CustomException(PAGE_NOT_FOUND);  // 404
    }

    assetRepository.deleteById(assetId);
  }
}
