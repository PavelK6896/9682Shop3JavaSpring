package app.web.pavelk.shop3.entity.user;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.Collection;


//кешь второго уровня////////////////////////
//READ_ONLY: Используется только для сущностей, которые никогда не меняются, в противном случае будет брошено исключение;
//NONSTRICT_READ_WRITE: Сущность обновляет свое состояние в кэше после того как транзакция фиксируется в БД. Возможны случаи, при которых параллельные транзакции будут видеть устаревшие данные в кэше.
//READ_WRITE: Когда закэшированная сущность изменяет свое состояние, в кэше делается soft lock и при попытке параллельных транзакций запросить данные из кэша, сущность будет возвращена напрямую из БД;
//TRANSACTIONAL: Гарантирует полную изоляцию транзакций, за это полностью отвечает provider.
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
///////////////////////////
@Entity
@Data
@NoArgsConstructor
@Table(name = "users")
@EqualsAndHashCode(of = {"id"}) // уникальность
@ToString(of = {"id", "firstName", "phone"})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "phone")
    private String phone;

    @Column(name = "password")
    private String password;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Embedded // добавляет свойства из другой таблице
    @AttributeOverrides({//переопределяем имя
            @AttributeOverride( name = "city", column = @Column(name = "address_city")),
            @AttributeOverride( name = "street", column = @Column(name = "address_street"))
    })
    private Address address;

    @ManyToMany
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Collection<Role> roles;

    public String getFullName() {
        return String.format("%s %s", firstName, lastName);
    }
}
