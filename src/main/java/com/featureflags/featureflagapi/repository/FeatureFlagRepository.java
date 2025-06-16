package com.featureflags.featureflagapi.repository;

import com.featureflags.featureflagapi.model.FeatureFlag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeatureFlagRepository extends JpaRepository<FeatureFlag, String> {
}
