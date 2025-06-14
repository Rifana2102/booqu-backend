package com.booqu.booqu_backend.model.master;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MasterResponse {
    private Long id;

    private String code;

    private String name;
}
