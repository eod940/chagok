package org.babyshark.chagok.domain.asset.dto;

import java.math.BigDecimal;
import java.util.List;
import lombok.Builder;
import org.babyshark.chagok.domain.member.domain.Member;

@Builder
public record AssetRequest(BigDecimal monthlyAllowance,
                           boolean isEssential,
                           List<String> assetCategoryNames,
                           String fixedExpensesCategory,
                           boolean hasItem,
                           BigDecimal itemPrice,
                           BigDecimal savings,
                           Member member) {
}
