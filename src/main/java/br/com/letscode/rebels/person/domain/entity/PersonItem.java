package br.com.letscode.rebels.person.domain.entity;

import br.com.letscode.rebels.item.domain.entity.Item;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor
public class PersonItem {

    @EmbeddedId
    @Setter
    @Getter
    private PersonItemID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId("person_id")
    @Getter
    @Setter
    private Person person;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId("item_id")
    @Getter
    @Setter
    private Item item;

    @Getter
    @Setter
    private int quantity;

    @Builder
    public PersonItem(PersonItemID personItemID, int quantity){
        this.id = personItemID;
        this.quantity = quantity;
    }
}