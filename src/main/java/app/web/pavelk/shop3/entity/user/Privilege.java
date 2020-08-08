package app.web.pavelk.shop3.entity.user;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Data
@NoArgsConstructor
@Table(name = "privileges1")
@EqualsAndHashCode(of = {"id"})
public class Privilege {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

//    @ManyToMany(mappedBy = "privileges1")
//    private Collection<Role> roles;

}
