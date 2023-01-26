package org.example.ensabillingservice.dto;

import lombok.*;
import org.example.ensabillingservice.entities.Customer;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.math.BigDecimal;
import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class InvoiceRequestDTO {

    private BigDecimal amount;
    private String customerId;

}
