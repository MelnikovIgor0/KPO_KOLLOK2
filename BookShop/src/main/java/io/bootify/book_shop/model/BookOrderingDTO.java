package io.bootify.book_shop.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class BookOrderingDTO {

    private Long id;

    @NotNull
    private Long bookId;

    @NotNull
    @Max(100)
    @Min(1)
    private Long numberBooks;

    @NotNull
    private Long cartId;

    private Long orderId;

    private Long bookUUID;

}
