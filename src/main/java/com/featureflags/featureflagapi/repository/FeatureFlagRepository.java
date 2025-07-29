package com.featureflags.featureflagapi.repository;

import com.featureflags.featureflagapi.model.FeatureFlag;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface FeatureFlagRepository extends JpaRepository<FeatureFlag, String> {
    List<FeatureFlag> findByTeamName(String teamName); 
}
