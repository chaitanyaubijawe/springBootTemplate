package com.smartdubai.assesment.eShop.controller.response;

import com.smartdubai.assesment.eShop.dto.BookDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CheckoutDetails {

    private BigDecimal    orderAmount;
    private BigDecimal    totalDiscount;
    private List<BookDto> books;

}
