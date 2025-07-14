package com.epam.cora.entity.metadata;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FireCloudClass {
    PARTICIPANT("participant", "entity:participant_id"), SAMPLE("sample", "entity:sample_id"), PAIR("pair", "entity:pair_id"), PARTICIPANT_SET("participant_set_entity", "entity:participant_set_id", "participant_set_membership", "membership:participant_set_id", "participant"), SAMPLE_SET("sample_set_entity", "entity:sample_set_id", "sample_set_membership", "membership:sample_set_id", "sample"), PAIR_SET("pair_set_entity", "entity:pair_set_id", "pair_set_membership", "membership:pair_set_id", "pair");

    private String fileName;
    private String headerEntityId;
    private String membershipFileName;
    private String membershipHeaderEntityId;
    private String membershipEntity;

    FireCloudClass(String fileName, String headerEntityId) {
        this.fileName = fileName;
        this.headerEntityId = headerEntityId;
    }
}
