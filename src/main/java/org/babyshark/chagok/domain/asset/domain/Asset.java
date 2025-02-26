package org.babyshark.chagok.domain.asset.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.math.BigDecimal;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.babyshark.chagok.domain.asset.dto.AssetRequest;
import org.babyshark.chagok.domain.member.domain.Member;
import org.babyshark.chagok.global.auditing.BaseEntity;

@Entity
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Asset extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long assetId;

  @ManyToOne
  private Member member;  // 사용자

  @OneToMany(mappedBy = "asset")
  private List<AssetCategory> assetCategories;  // 고정지출 항목들

  private BigDecimal monthlyAllowance;  // 한 달 용돈
  private boolean isEssential;  // 필수 지출 여부(고정 지출)
  private String fixedExpensesCategory;  // 고정지출 항목(필수지출이 true)

  private boolean hasItem;  // 사고 싶은 물건 있는지 여부
  private String itemName;  // 사고 싶은 물건 이름
  private BigDecimal itemPrice;  // 사고 싶은 물건 금액
  private BigDecimal savings;  // 저축액

  public static Asset create(AssetRequest assetRequest, List<AssetCategory> assetCategories) {
    return Asset.builder()
        .monthlyAllowance(assetRequest.monthlyAllowance())
        .isEssential(assetRequest.isEssential())
        .assetCategories(assetCategories)
        .fixedExpensesCategory(assetRequest.fixedExpensesCategory())
        .hasItem(assetRequest.hasItem())
        .itemPrice(assetRequest.itemPrice())
        .savings(assetRequest.savings())
        .member(assetRequest.member())
        .build();
  }
}
