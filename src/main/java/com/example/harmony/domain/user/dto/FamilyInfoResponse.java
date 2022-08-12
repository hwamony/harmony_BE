package com.example.harmony.domain.user.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FamilyInfoResponse {

    private String familyName;
    private List<MembersResponse> members;

    public FamilyInfoResponse(String familyName, List<MembersResponse> members) {
        this.familyName = familyName;
        this.members = members;
    }

}
