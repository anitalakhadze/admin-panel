package com.example.demoadminpanel.transaction.model;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDetails {
    private Long userId;
    private Integer status;
}
