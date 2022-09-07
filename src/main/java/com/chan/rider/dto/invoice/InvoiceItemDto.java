package com.chan.rider.dto.invoice;

import com.chan.rider.domain.Address;
import com.chan.rider.domain.Invoice;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class InvoiceItemDto {
    @NotEmpty
    private Long invoiceId;

    @NotEmpty
    private String invoiceCode;

    @NotEmpty
    private LocalDate deliveryDate;

    private Address storeAddress;

    private String storeLocalCode;

    private Address customerAddress;

    private String customerLocalCode;

    public InvoiceItemDto(Invoice invoice) {
        this.invoiceId = invoice.getId();
        this.invoiceCode = invoice.getInvoiceCode();
        this.deliveryDate = invoice.getDeliveryDate();
        this.storeAddress = invoice.getStoreAddress();
        this.storeLocalCode = invoice.getStoreLocalCode();
        this.customerAddress = invoice.getCustomerAddress();
        this.customerLocalCode = invoice.getCustomerLocalCode();
    }

    public Invoice toEntity(){
        Invoice invoice = new Invoice();
        invoice.setLogisticsInvoiceId(this.invoiceId);
        invoice.setInvoiceCode(this.invoiceCode);
        invoice.setDeliveryDate(this.deliveryDate);
        invoice.setStoreAddress(this.storeAddress);
        invoice.setStoreLocalCode(this.storeLocalCode);
        invoice.setCustomerAddress(this.customerAddress);
        invoice.setCustomerLocalCode(this.customerLocalCode);

        invoice.setName("a");

        return invoice;
    }
}
