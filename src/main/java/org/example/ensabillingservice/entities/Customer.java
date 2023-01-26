package org.example.ensabillingservice.entities;


import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Customer {
    private String id;
    private String name;
    private String email;
}
