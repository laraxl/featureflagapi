package com.featureflags.featureflagapi.controller;

import com.featureflags.featureflagapi.model.FeatureFlag;
import com.featureflags.featureflagapi.repository.FeatureFlagRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/flags")
public class TestConnectionController {

    private final FeatureFlagRepository repository;

    public TestConnectionController(FeatureFlagRepository repository) {
        this.repository = repository;
    }

    // GET all flags
    @GetMapping
    public List<FeatureFlag> getAllFlags() {
        return repository.findAll();
    }

    // POST a new flag
    @PostMapping
    public FeatureFlag createFlag(@RequestBody FeatureFlag flag) {
        flag.setCreatedAt(OffsetDateTime.now());
        flag.setUpdatedAt(OffsetDateTime.now());
        return repository.save(flag);
    }

    // GET a flag by name
    @GetMapping("/{name}")
    public ResponseEntity<FeatureFlag> getFlagByName(@PathVariable String name) {
        Optional<FeatureFlag> flag = repository.findById(name);
        return flag.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // PUT (update) a flag by name
    @PutMapping("/{name}")
    public ResponseEntity<FeatureFlag> updateFlag(@PathVariable String name, @RequestBody FeatureFlag updatedFlag) {
        return repository.findById(name).map(existingFlag -> {
            existingFlag.setDescription(updatedFlag.getDescription());
            existingFlag.setStatus(updatedFlag.getStatus());
            existingFlag.setVersion(updatedFlag.getVersion());
            existingFlag.setTeamName(updatedFlag.getTeamName());
            existingFlag.setUpdatedAt(OffsetDateTime.now());
            return ResponseEntity.ok(repository.save(existingFlag));
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // DELETE a flag by name
    @DeleteMapping("/{name}")
    public ResponseEntity<Void> deleteFlag(@PathVariable String name) {
        return repository.findById(name).map(flag -> {
            repository.delete(flag);
            return ResponseEntity.noContent().<Void>build();
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }



    // GET flags by team name
    @GetMapping("/team/{teamName}")
    public List<FeatureFlag> getFlagsByTeamName(@PathVariable String teamName) {
        return repository.findByTeamName(teamName);
    }

}

