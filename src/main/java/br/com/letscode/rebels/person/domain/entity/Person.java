package br.com.letscode.rebels.person.domain.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Entity
public class Person {

    public static final int NUMBER_MAX_OF_REPORTS = 3;
    @Getter
    @Id
    private final String id;

    @Getter
    private String name;

    @Getter
    private int age;

    @Getter
    @Enumerated(EnumType.STRING)
    private GenderEnum gender;

    @Getter
    @Setter
    @Embedded
    private Localization currentLocalization;

    @Getter
    @Setter
    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<PersonItem> inventory;

    @Getter
    private boolean rebel;

    @Getter
    private int reportCount;

    public Person(){
        this.id = UUID.randomUUID().toString();
        this.reportCount = 0;
        this.rebel = true;
    }

    @PreUpdate
    @PrePersist
    public void prepare(){
        this.name = this.name.toUpperCase();
        this.currentLocalization.setGalaxyName(this.currentLocalization.getGalaxyName().toUpperCase());
    }

    @Builder
    public Person(String name, int age, GenderEnum gender, Localization currentLocalization){
        this();
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.currentLocalization = currentLocalization;
    }

    public void betray(){
        if(this.rebel){
            this.reportCount++;
            if(this.reportCount == NUMBER_MAX_OF_REPORTS) {
                this.rebel = false;
            }
        }
    }


    public void updateLocalization(double latitude, double longitude, String galaxyName) {
        this.currentLocalization.setLatitude(latitude);
        this.currentLocalization.setLongitude(longitude);
        this.currentLocalization.setGalaxyName(galaxyName);
    }

    public boolean isRebel(){
        return rebel;
    }
}
