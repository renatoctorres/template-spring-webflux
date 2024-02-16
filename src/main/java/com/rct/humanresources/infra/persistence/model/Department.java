package com.rct.humanresources.infra.persistence.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Department - Spring Data Entity
 */
@ToString
@EqualsAndHashCode(of = {"id","name","description"})
@Getter
@Setter
@Document(value = "departments")
@AllArgsConstructor
@NoArgsConstructor
public class Department {
    @Id
    private Long id;
    private String name;
    private String description;
    private Long locationId;
    private Long managerId;
}
