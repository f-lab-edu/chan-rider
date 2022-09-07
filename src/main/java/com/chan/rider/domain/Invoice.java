package com.chan.rider.domain;

import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

@Entity
@Getter
@Table(name = "invoices")
public class Invoice extends NameEntity{

    @Column(name = "logistics_invoice_id")
    private Long logisticsInvoiceId;

    private String invoiceCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "work_request_id")
    private WorkRequest workRequest;

    private LocalDate deliveryDate;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name="doroAddress", column=@Column(name = "store_doroAddress")),
            @AttributeOverride(name="sigunguCode", column=@Column(name = "store_sigunguCode"))
    })
    private Address storeAddress;

    private String storeLocalCode;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name="doroAddress", column=@Column(name = "customer_doroAddress")),
            @AttributeOverride(name="sigunguCode", column=@Column(name = "customer_sigunguCode"))
    })
    private Address customerAddress;

    private String customerLocalCode;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private InvoiceStatusEnum status = InvoiceStatusEnum.RECEPTION;


    public void setLogisticsInvoiceId(Long logisticsInvoiceId) {
        this.logisticsInvoiceId = logisticsInvoiceId;
    }

    public void setWorkRequest(WorkRequest workRequest) {
        this.workRequest = workRequest;
    }

    public void setInvoiceCode(String invoiceCode) {
        this.invoiceCode = invoiceCode;
    }

    public void setDeliveryDate(LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public void setStoreAddress(Address storeAddress) {
        this.storeAddress = storeAddress;
    }

    public void setStoreLocalCode(String storeLocalCode) {
        this.storeLocalCode = storeLocalCode;
    }

    public void setCustomerAddress(Address customerAddress) {
        this.customerAddress = customerAddress;
    }

    public void setCustomerLocalCode(String customerLocalCode) {
        this.customerLocalCode = customerLocalCode;
    }

    public void setStatus(InvoiceStatusEnum status) {
        this.status = status;
    }
}
