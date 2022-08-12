package com.example.harmony.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class MembersResponse {

    private long userId;
    private String name;
    private String role;

}
