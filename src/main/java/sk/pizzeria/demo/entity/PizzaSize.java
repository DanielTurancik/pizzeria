package sk.pizzeria.demo.entity;
import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "pizza_sizes")
public class PizzaSize {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pizza_size_id")
    private Integer id;

    @Column(name = "size_name", nullable = false, length = 10)
    private String sizeName;

    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "diameter_cm", nullable = false)
    private Integer diameterCm;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pizzas_pizza_id", nullable = false)
    private Pizza pizza;

    // getters/setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getSizeName() { return sizeName; }
    public void setSizeName(String sizeName) { this.sizeName = sizeName; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public Integer getDiameterCm() { return diameterCm; }
    public void setDiameterCm(Integer diameterCm) { this.diameterCm = diameterCm; }

    public Pizza getPizza() { return pizza; }
    public void setPizza(Pizza pizza) { this.pizza = pizza; }
}
