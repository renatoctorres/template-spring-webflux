package com.rct.humanresources.infra.persistence.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Location - Spring Data Entity
 */
@Getter
@Setter
@Document(collection = "locations")
@AllArgsConstructor
@NoArgsConstructor
public class Location {
    @Id
    private Long id;
    private String street;
    private String postalCode;
    private Long cityId;
}
