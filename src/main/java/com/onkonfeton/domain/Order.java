package com.onkonfeton.domain;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

public class Order {
    private UUID id;
    private List<Product> products;
    private OffsetDateTime createDate;
}
