package org.babyshark.chagok.domain.asset.repository;

import java.util.List;
import org.babyshark.chagok.domain.asset.domain.Asset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssetRepository extends JpaRepository<Asset, Long> {

  List<Asset> findAllByMember_MemberId(long memberId);
}