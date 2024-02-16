package com.rct.humanresources.infra.persistence.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Country - Mongodb Reactive Entity
 */
@Getter
@Setter
@Document(collection = "countries")
@AllArgsConstructor
@NoArgsConstructor
public class Country {
    @Id
    private Long id;
    private String name;
}
