package com.booqu.booqu_backend.dto;

import java.util.List;

public interface BookWithGenresDTO {
    Long getId();
    String getTitle();
    String getAuthorCode();
    String getAuthorName();
    String getImagePath();
    String getPdfPath();
    String getDescription();
    List<String> getGenres();  // Important: Spring supports List<String> for array_agg
}
