package br.com.letscode.rebels.person.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PersonItemID implements Serializable{

    @Column(name = "person_id")
    private String personId;

    @Column(name = "item_id")
    private Long item;
}