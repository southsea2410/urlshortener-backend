package org.nam.urlshortener.dto;

import lombok.Data;
import org.springframework.lang.Nullable;

import java.util.Map;

@Data
public class MetabasePayloadDto {
    public Map<String, Object> resource;

    @Nullable
    public Map<String, Object> params;
}