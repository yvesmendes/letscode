package br.com.letscode.rebels.item.domain.entity;

import br.com.letscode.rebels.person.domain.entity.PersonItem;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@SequenceGenerator(name="item_id_seq", initialValue=5, allocationSize=100)
@NoArgsConstructor
public class Item {

    @Getter
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator="item_id_seq")
    private Long id;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private int points;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
    private Set<PersonItem> personItems = new HashSet<>();

    @PreUpdate
    @PrePersist
    private void prepareItem(){
            this.name = name.toUpperCase();
    }

    public Item(Long id){
        this.id = id;
    }

    @Builder
    public Item(String name, int points){
        this.name = name;
        this.points = points;
    }
}
