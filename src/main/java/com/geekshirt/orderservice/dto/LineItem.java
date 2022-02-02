package com.geekshirt.orderservice.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@ApiModel(description = "Class that represents an item included in the order")
@Data
@AllArgsConstructor
public class LineItem {

    @ApiModelProperty(notes = "UPC (Universial Product Code), Length 12 digits",example = "134235324332",required = true, position = 0)
    private String upc;

    @ApiModelProperty(notes = "Quantity",example = "5",required = true, position = 1)
    private int quantity;

    @ApiModelProperty(notes = "Price",example = "20.00",required = true, position = 2)
    private Double price;

}
