package org.babyshark.chagok.domain.asset.dto;

import java.util.List;
import org.babyshark.chagok.domain.asset.domain.AssetCategory;

public record AssetResponse(
    long assetId,
    List<AssetCategory> assetCategories,
    String itemName,
    String fixedExpensesCategory
) {

}
