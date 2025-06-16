package com.featureflags.featureflagapi.controller;

import com.featureflags.featureflagapi.model.FeatureFlag;
import com.featureflags.featureflagapi.repository.FeatureFlagRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test-db")
public class TestConnectionController {

    private final FeatureFlagRepository repo;

    public TestConnectionController(FeatureFlagRepository repo) {
        this.repo = repo;
    }

    @PostMapping
    public FeatureFlag saveTestFlag() {
        FeatureFlag flag = new FeatureFlag();
        flag.setName("test_flag");
        flag.setEnabled(true);
        return repo.save(flag);
    }

    @GetMapping
    public FeatureFlag getTestFlag() {
        return repo.findById("test_flag").orElse(null);
    }
}
