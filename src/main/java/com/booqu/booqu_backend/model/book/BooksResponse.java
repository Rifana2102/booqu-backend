package com.booqu.booqu_backend.model.book;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BooksResponse {
    
    private Long id;

    private String title;

    private List<String> genres;

    private String author;

    private String imagePath;

}