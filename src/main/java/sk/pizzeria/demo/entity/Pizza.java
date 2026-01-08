package sk.pizzeria.demo.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pizzas")
public class Pizza {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pizza_id")
    private Integer id;

    @Column(name = "name", nullable = false, length = 45)
    private String name;

    @Column(name = "slug", nullable = false, length = 45)
    private String slug;

    @Column(name = "description", length = 255)
    private String description;

    @Column(name = "image_url", length = 255)
    private String imageUrl;

    @Column(name = "active", nullable = false)
    private boolean active = true;

    // NEW: category + tags/brand
    @Enumerated(EnumType.STRING)
    @Column(name = "category", length = 30)
    private PizzaCategory category;

    // Comma-separated tags, e.g. "Vegetarian,Italian,Spicy"
    @Column(name = "tags_csv", length = 255)
    private String tagsCsv;

    @OneToMany(mappedBy = "pizza", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PizzaSize> sizes = new ArrayList<>();

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getSlug() { return slug; }
    public void setSlug(String slug) { this.slug = slug; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }

    public PizzaCategory getCategory() { return category; }
    public void setCategory(PizzaCategory category) { this.category = category; }

    public String getTagsCsv() { return tagsCsv; }
    public void setTagsCsv(String tagsCsv) { this.tagsCsv = tagsCsv; }

    public List<PizzaSize> getSizes() { return sizes; }
    public void setSizes(List<PizzaSize> sizes) { this.sizes = sizes; }
}
