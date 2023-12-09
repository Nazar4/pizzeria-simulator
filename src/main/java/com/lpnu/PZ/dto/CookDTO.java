package com.lpnu.PZ.dto;

import com.lpnu.PZ.domain.CookState;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CookDTO {
    private String cookId;
    private CookState cookState;

    public CookDTO(String cookId, CookState cookState)
    {
        this.cookId = cookId;
        this.cookState = cookState;
    }
}
