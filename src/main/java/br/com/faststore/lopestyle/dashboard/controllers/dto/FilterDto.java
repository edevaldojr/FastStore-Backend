package br.com.faststore.lopestyle.dashboard.controllers.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FilterDto {
    private int page;
    private int pageSize;
    private String search;
}
