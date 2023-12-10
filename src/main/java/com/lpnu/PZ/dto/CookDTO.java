package com.lpnu.PZ.dto;

import com.lpnu.PZ.domain.Cook;
import com.lpnu.PZ.domain.CookState;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CookDTO {
    private String cookId;
    private CookState cookState;

    public static CookDTO mapToCookDTO(final Cook cook) {
        final CookDTO cookDTO = new CookDTO();
        cookDTO.setCookId(cook.getCookId());
        cookDTO.setCookState(cook.getCookState());
        return cookDTO;
    }
}
