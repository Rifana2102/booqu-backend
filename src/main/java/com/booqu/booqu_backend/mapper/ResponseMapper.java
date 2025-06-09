package com.booqu.booqu_backend.mapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.booqu.booqu_backend.model.book.BookDetailResponse;
import com.booqu.booqu_backend.model.book.BooksResponse;

public class ResponseMapper {

    public static List<BooksResponse> ToBookResponseListMapper(List<Object[]> rows) {
        return rows.stream()
            .map(row -> {
                Long id = ((Number) row[0]).longValue();
                String title = (String) row[1];
                String authorName = (String) row[3];
                String imagePath = (String) row[4];

                List<String> genres = new ArrayList<>();
                Object genreObj = row[7];

                if (genreObj instanceof String[] genreArray) {
                    genres = Arrays.asList(genreArray);
                } else if (genreObj instanceof java.sql.Array sqlArray) {
                    try {
                        String[] genreArray = (String[]) sqlArray.getArray();
                        genres = Arrays.asList(genreArray);
                    } catch (Exception e) {
                        genres = new ArrayList<>();
                    }
                } else if (genreObj instanceof String rawGenres) {
                    rawGenres = rawGenres.replace("{", "").replace("}", "");
                    genres = rawGenres.isEmpty() ? new ArrayList<>() : Arrays.asList(rawGenres.split(","));
                }

                return new BooksResponse(id, title, genres, authorName, imagePath);
            })
            .collect(Collectors.toList());
    }



    public static BookDetailResponse ToBookResponseMapper(Object[] row, boolean isLogin) {
        if (row.length == 1 && row[0] instanceof Object[] innerRow) {
            row = innerRow;
        }

        Long id = ((Number) row[0]).longValue();
        String title = (String) row[1];
        String description = (String) row[2];
        String authorName = (String) row[3];
        String imagePath = (String) row[4];
        String pdfPath = (String) row[5];

        List<String> genres = new ArrayList<>();
        Object genreObj = row[6];  // genres should be at index 6 based on your query

        if (genreObj instanceof String[] genreArray) {
            genres = Arrays.asList(genreArray);
        } else if (genreObj instanceof java.sql.Array sqlArray) {
            try {
                String[] genreArray = (String[]) sqlArray.getArray();
                genres = Arrays.asList(genreArray);
            } catch (Exception e) {
                genres = new ArrayList<>();
            }
        } else if (genreObj instanceof String rawGenres) {
            rawGenres = rawGenres.replace("{", "").replace("}", "");
            genres = rawGenres.isEmpty() ? new ArrayList<>() : Arrays.asList(rawGenres.split(","));
        }

        return new BookDetailResponse(
            id,
            title,
            genres,
            description,
            authorName,
            imagePath,
            isLogin ? pdfPath : null
        );
    }
}
