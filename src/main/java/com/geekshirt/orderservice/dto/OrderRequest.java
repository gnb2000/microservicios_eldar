package com.geekshirt.orderservice.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@ApiModel(description = "Class representing an order to be processed")
public class OrderRequest {

    @NotNull
    @NotBlank
    @ApiModelProperty(notes = "Account id", example = "121212", required = true)
    private String accountId;

    @ApiModelProperty(notes = "Items included in the order", required = true)
    private List<LineItem> items;
}
