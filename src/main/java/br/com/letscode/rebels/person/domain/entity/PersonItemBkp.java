//package br.com.letscode.rebels.person.domain.entity;
//
//import br.com.letscode.rebels.item.domain.entity.Item;
//import lombok.Getter;
//import lombok.Setter;
//
//import javax.persistence.*;
//
//@Entity
//@SequenceGenerator(name="person_item_id_seq", initialValue=1, allocationSize=100)
//public class PersonItemBkp {
//
//    @Id
//    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator="person_item_id_seq")
//    @Getter
//    private Long id;
//
//    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    @JoinColumn(name = "person_id")
//    @Getter
//    @Setter
//    private Person person;
//
//    @ManyToOne(fetch = FetchType.EAGER, optional = false)
//    @JoinColumn(name = "item_id")
//    @Getter
//    @Setter
//    private Item item;
//
//    @Getter
//    @Setter
//    private int quantity;
//}