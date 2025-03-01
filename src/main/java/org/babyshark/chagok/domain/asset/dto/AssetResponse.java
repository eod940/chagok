package org.babyshark.chagok.domain.asset.dto;

import java.time.LocalDateTime;
import java.util.List;
import org.babyshark.chagok.domain.asset.domain.Asset;
import org.babyshark.chagok.domain.asset.domain.AssetCategory;

public record AssetResponse(
    long assetId,
    List<AssetCategory> assetCategories,
    String monthlyAllowance,
    boolean isEssential,
    String fixedExpensesAmount,
    boolean hasItem,
    String itemName,
    String itemPrice,
    String savings,
    String assistQuery,
    LocalDateTime createdDateTime
) {

  public static AssetResponse create(Asset asset) {

    return new AssetResponse(asset.getAssetId(), asset.getAssetCategories(),
        asset.getMonthlyAllowance().toString(), asset.isEssential(),
        asset.getFixedExpensesCategory(), asset.isHasItem(), asset.getItemName(),
        asset.getItemPrice().toString(),
        asset.getSavings().toString(), asset.getAssistQuery(), asset.getCreatedDateTime());
  }

}
