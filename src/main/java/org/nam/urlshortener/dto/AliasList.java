package org.nam.urlshortener.dto;

import lombok.Data;

import java.util.List;

@Data
public class AliasList {
    private List<String> aliases;
}
