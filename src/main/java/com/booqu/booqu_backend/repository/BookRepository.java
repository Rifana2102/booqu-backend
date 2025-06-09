package com.booqu.booqu_backend.repository;

import com.booqu.booqu_backend.dto.BookWithGenresDTO;
import com.booqu.booqu_backend.entity.BookEntity;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, Long> {
    List<BookEntity> findAll();

    @Query(value = """
        SELECT 
            b.id,
            b.title,
            b.author_code,
            a.name AS author_name,
            b.image_path,
            b.pdf_path,
            b.description,
            array_agg(DISTINCT g.name) AS genres
        FROM books b
        JOIN master_authors a ON a.code = b.author_code
        LEFT JOIN book_genres bg ON bg.book_id = b.id
        LEFT JOIN master_genres g ON g.code = bg.genre_code
        WHERE (:authorCode IS NULL OR :authorCode = '' OR a.code = :authorCode)
        AND (
            (:genreCodes IS NULL OR cardinality(:genreCodes) = 0)
            OR g.code = ANY(:genreCodes)
        )
        AND (
            :keyword IS NULL OR :keyword = '' OR
            LOWER(b.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR
            LOWER(a.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR
            LOWER(g.name) LIKE LOWER(CONCAT('%', :keyword, '%'))
        )
        GROUP BY b.id, b.title, b.author_code, a.name, b.image_path, b.pdf_path, b.description
    """, nativeQuery = true)
    List<Object[]> findBooksWithFilter(
        @Param("authorCode") String authorCode,
        @Param("genreCodes") String[] genreCodes,
        @Param("keyword") String keyword);

    @Query(value = """
        SELECT 
            b.id,
            b.title,
            b.description,
            a.name AS author_name,
            b.image_path,
            b.pdf_path,
            array_agg(DISTINCT g.name) AS genres
        FROM books b
        JOIN master_authors a ON a.code = b.author_code
        LEFT JOIN book_genres bg ON bg.book_id = b.id
        LEFT JOIN master_genres g ON g.code = bg.genre_code
        WHERE b.id = :id
        GROUP BY b.id, b.title, b.description, a.name, b.image_path, b.pdf_path
        ORDER BY b.created_at DESC
        """, nativeQuery = true)
    Optional<Object[]> findBookWithGenresById(@Param("id") Long id);

    @Query(value = """
            SELECT 
                b.id,
                b.title,
                b.author_code,
                a.name AS author_name,
                b.image_path,
                b.pdf_path,
                b.description,
                array_agg(DISTINCT g.name) AS genres
            FROM books b
            JOIN master_authors a ON a.code = b.author_code
            LEFT JOIN book_genres bg ON bg.book_id = b.id
            LEFT JOIN master_genres g ON g.code = bg.genre_code
            JOIN transactions t ON t.book_id = b.id
            JOIN master_transaction_types mtt ON mtt.code = t.transaction_type_code
            WHERE mtt.code = 'LOAN'
            GROUP BY b.id, b.title, b.author_code, a.name, b.image_path, b.pdf_path, b.description
            ORDER BY COUNT(t.id) DESC
        """, nativeQuery = true)
    List<Object[]> findFavoriteLoanBooks();

    @Query(value = """
        SELECT 
            b.id,
            b.title,
            b.author_code,
            a.name AS author_name,
            b.image_path,
            b.pdf_path,
            b.description,
            array_agg(DISTINCT g.name) AS genres
        FROM books b
        JOIN master_authors a ON a.code = b.author_code
        LEFT JOIN book_genres bg ON bg.book_id = b.id
        LEFT JOIN master_genres g ON g.code = bg.genre_code
        JOIN transactions t ON t.book_id = b.id
        JOIN master_transaction_types mtt ON mtt.code = t.transaction_type_code
        WHERE mtt.code = 'LOAN'
        AND b.author_code IN (
            SELECT b2.author_code
            FROM books b2
            JOIN transactions t2 ON t2.book_id = b2.id
            JOIN master_transaction_types mtt2 ON mtt2.code = t2.transaction_type_code
            WHERE mtt2.code = 'LOAN'
            GROUP BY b2.author_code
            ORDER BY COUNT(t2.id) DESC
        )
        GROUP BY b.id, b.title, b.author_code, a.name, b.image_path, b.pdf_path, b.description
        ORDER BY COUNT(t.id) DESC
    """, nativeQuery = true)
    List<Object[]> findFavoriteAuthorsBooks();

    @Query(value = """
        SELECT 
            b.id,
            b.title,
            b.author_code,
            a.name AS author_name,
            b.image_path,
            b.pdf_path,
            b.description,
            array_agg(DISTINCT g.name) AS genres
        FROM books b
        JOIN master_authors a ON a.code = b.author_code
        LEFT JOIN book_genres bg ON bg.book_id = b.id
        LEFT JOIN master_genres g ON g.code = bg.genre_code
        JOIN loans l ON l.book_id = b.id
        WHERE l.user_id = :userId
        AND l.is_returned = false
        GROUP BY b.id, b.title, b.author_code, a.name, b.image_path, b.pdf_path, b.description
    """, nativeQuery = true)
    List<Object[]> findActiveLoanBooksByUser(@Param("userId") Long userId);

    @Query(value = """
        SELECT 
            b.id,
            b.title,
            b.author_code,
            a.name AS author_name,
            b.image_path,
            b.pdf_path,
            b.description,
            array_agg(DISTINCT g.name) AS genres
        FROM books b
        JOIN master_authors a ON a.code = b.author_code
        LEFT JOIN book_genres bg ON bg.book_id = b.id
        LEFT JOIN master_genres g ON g.code = bg.genre_code
        JOIN reservations r ON r.book_id = b.id
        WHERE r.user_id = :userId
        AND r.is_loaned = false
        AND r.is_cancelled = false
        GROUP BY b.id, b.title, b.author_code, a.name, b.image_path, b.pdf_path, b.description
    """, nativeQuery = true)
    List<Object[]> findReservedBooksNotYetLoanedByUser(@Param("userId") Long userId);

    @Query(value = """
        SELECT 
            b.id,
            b.title,
            b.author_code,
            a.name AS author_name,
            b.image_path,
            b.pdf_path,
            b.description,
            array_agg(DISTINCT g.name) AS genres
        FROM transactions t
        JOIN books b ON b.id = t.book_id
        JOIN master_authors a ON a.code = b.author_code
        LEFT JOIN book_genres bg ON bg.book_id = b.id
        LEFT JOIN master_genres g ON g.code = bg.genre_code
        WHERE t.user_id = :userId
        GROUP BY b.id, b.title, b.author_code, a.name, b.image_path, b.pdf_path, b.description
        ORDER BY MAX(t.created_at) DESC
    """, nativeQuery = true)
    List<Object[]> findBookTransactionHistoryByUser(@Param("userId") Long userId);

}
