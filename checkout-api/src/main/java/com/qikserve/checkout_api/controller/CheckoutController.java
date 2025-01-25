package com.qikserve.checkout_api.controller;

import com.qikserve.checkout_api.exception.ExceptionResponse;
import com.qikserve.checkout_api.model.CheckoutItem;
import com.qikserve.checkout_api.model.CheckoutItemList;
import com.qikserve.checkout_api.model.CheckoutResponse;
import com.qikserve.checkout_api.service.CheckoutService;
import com.qikserve.checkout_api.util.MediaType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Checkout API")
@RestController
@RequestMapping("/api/checkout")
public class CheckoutController {

    @Autowired
    private CheckoutService checkoutService;

    @Operation(
            summary = "Calculate the checkout total",
            description = "This endpoint calculates the subtotal, total savings, and total price for a list of checkout items."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Checkout calculated successfully",
                    content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON,
                                    schema = @Schema(implementation = CheckoutResponse.class)
                            ),
                            @Content(
                                    mediaType = MediaType.APPLICATION_XML,
                                    schema = @Schema(implementation = CheckoutResponse.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid request data",
                    content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON,
                                    schema = @Schema(implementation = ExceptionResponse.class)
                            ),
                            @Content(
                                    mediaType = MediaType.APPLICATION_XML,
                                    schema = @Schema(implementation = ExceptionResponse.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Product not found",
                    content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON,
                                    schema = @Schema(implementation = ExceptionResponse.class)
                            ),
                            @Content(
                                    mediaType = MediaType.APPLICATION_XML,
                                    schema = @Schema(implementation = ExceptionResponse.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON,
                                    schema = @Schema(implementation = ExceptionResponse.class)
                            ),
                            @Content(
                                    mediaType = MediaType.APPLICATION_XML,
                                    schema = @Schema(implementation = ExceptionResponse.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "502",
                    description = "Wiremock Unavailable",
                    content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON,
                                    schema = @Schema(implementation = ExceptionResponse.class)
                            ),
                            @Content(
                                    mediaType = MediaType.APPLICATION_XML,
                                    schema = @Schema(implementation = ExceptionResponse.class)
                            )
                    }
            )
    })
    @PostMapping(
            produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML},
            consumes = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML}
    )
    public CheckoutResponse calculateCheckout(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "List of checkout items with productId and quantity",
                    required = true,
                    content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON,
                                    schema = @Schema(implementation = CheckoutItem[].class)
                            ),
                            @Content(
                                    mediaType = MediaType.APPLICATION_XML,
                                    schema = @Schema(implementation = CheckoutItemList.class)
                            )
                    }
            )
            @RequestBody List<CheckoutItem> items
    ) {
        return checkoutService.calculateTotal(items);
    }
}