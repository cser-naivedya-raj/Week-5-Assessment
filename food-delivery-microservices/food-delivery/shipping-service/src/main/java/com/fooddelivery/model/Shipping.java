package com.fooddelivery.model;

import jakarta.persistence.*;

@Entity
@Table(name = "shippings")
public class Shipping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private Long deliveryId;

    private String address;
    private String estimatedTime;
    private String status;

    public Shipping() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getDeliveryId() { return deliveryId; }
    public void setDeliveryId(Long deliveryId) { this.deliveryId = deliveryId; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getEstimatedTime() { return estimatedTime; }
    public void setEstimatedTime(String estimatedTime) { this.estimatedTime = estimatedTime; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
