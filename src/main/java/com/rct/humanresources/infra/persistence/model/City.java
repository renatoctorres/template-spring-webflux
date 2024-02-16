package com.rct.humanresources.infra.persistence.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * City - Mongodb Reactive Entity
 */
@Getter
@Setter
@Document(collection = "cities")
@AllArgsConstructor
@NoArgsConstructor
public class City {
    @Id
    private Long id;
    private String name;
    private Long stateId;
}
