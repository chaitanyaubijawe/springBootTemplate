package com.smartdubai.assesment.eShop.controller.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiError {

    private String errorCode;
    private String errorDescription;
}
