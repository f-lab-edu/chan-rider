package com.chan.rider.dto.invoice;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MatchingResponseDto {

    List<InvoiceItemDto> invoiceItemList = new ArrayList<>();

    int invoiceCount;

}
