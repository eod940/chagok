package org.babyshark.chagok.domain.asset.dto;

import java.math.BigDecimal;
import lombok.Builder;
import org.babyshark.chagok.domain.member.domain.Member;

@Builder
public record AssetRequest(BigDecimal monthlyAllowance, boolean isEssential, String fixedExpensesCategory,
                           boolean hasItem, BigDecimal itemPrice, BigDecimal savings, Member member) {
}
