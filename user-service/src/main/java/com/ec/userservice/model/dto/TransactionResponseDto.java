package com.ec.userservice.model.dto;


import com.ec.userservice.model.TransactionStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class TransactionResponseDto {

    private Integer userId;
    private Integer amount;
    private TransactionStatus status;
}