package org.babyshark.chagok.domain.asset.repository;

import java.util.List;
import org.babyshark.chagok.domain.asset.domain.AssetCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssetCategoryRepository extends JpaRepository<AssetCategory, Long> {

  List<AssetCategory> findAssetCategoryByName(String name);

}
