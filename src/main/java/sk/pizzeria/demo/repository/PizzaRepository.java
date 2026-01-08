package sk.pizzeria.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sk.pizzeria.demo.entity.Pizza;
import sk.pizzeria.demo.entity.PizzaCategory;

import java.util.List;
import java.util.Optional;

public interface PizzaRepository extends JpaRepository<Pizza, Integer> {

    List<Pizza> findByActiveTrueOrderByNameAsc();

    Optional<Pizza> findBySlug(String slug);

    List<Pizza> findByNameContainingIgnoreCaseAndActiveTrue(String q);

    // combined filtering (name + category + tag), any param can be null/blank.
    @Query("""
        select p
        from Pizza p
        where p.active = true
          and (:q is null or :q = '' or lower(p.name) like lower(concat('%', :q, '%')))
          and (:category is null or p.category = :category)
          and (:tag is null or :tag = '' or lower(p.tagsCsv) like lower(concat('%', :tag, '%')))
        order by p.name asc
    """)
    List<Pizza> searchActive(
            @Param("q") String q,
            @Param("category") PizzaCategory category,
            @Param("tag") String tag
    );
}
