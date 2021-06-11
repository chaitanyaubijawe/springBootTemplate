package com.smartdubai.assesment.eShop.constants;

import java.math.BigDecimal;

public enum PromoDetails {
    TEN("TEN", new BigDecimal(10));

    private final String     promoCode;
    private final BigDecimal discount;

    PromoDetails (final String promoCode, final BigDecimal discount) {

        this.promoCode = promoCode;
        this.discount = discount;
    }

    public BigDecimal getDiscount(){
        return discount;
    }
    public String getPromoCode(){
        return promoCode;
    }



}
