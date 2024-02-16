package com.rct.humanresources.infra.persistence.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * State - Mongodb Reactive Entity
 */
@Getter
@Setter
@Document(collection = "states")
@AllArgsConstructor
@NoArgsConstructor
public class State {
    @Id
    private  Long id;
    private String name;
    private String acronym;
    private Long countryId;
}
