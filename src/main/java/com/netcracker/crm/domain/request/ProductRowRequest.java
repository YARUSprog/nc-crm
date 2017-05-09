package com.netcracker.crm.domain.request;

import com.netcracker.crm.domain.model.Address;

/**
 * @author Karpunets
 * @since 05.05.2017
 */
public class ProductRowRequest extends RowRequest {

    private Long statusId;
    private Boolean discountActive;
    private Long customerId;
    private Address address;

    private static final String BEGIN_SQL = "" +
            "SELECT p.id, p.title, p.default_price, d.title, d.percentage," +
            "g.name, d.active, p.status_id, p.description, p.discount_id, p.group_id " +
            "FROM product p " +
            "INNER JOIN statuses s ON p.status_id = s.id " +
            "LEFT JOIN groups g ON p.group_id = g.id " +
            "LEFT JOIN discount d ON p.discount_id = d.id";
    private static final String BEGIN_SQL_COUNT = "" +
            "SELECT count(*) " +
            "FROM product p " +
            "INNER JOIN statuses s ON p.status_id = s.id " +
            "LEFT JOIN groups g ON p.group_id = g.id " +
            "LEFT JOIN discount d ON p.discount_id = d.id";

    public ProductRowRequest() {
        super(new String[]{
                "p.id",
                "p.title",
                "p.default_price",
                "d.title",
                "d.percentage",
                "g.name"
        });
    }


    public Long getStatusId() {
        return statusId;
    }

    public void setStatusId(Long statusId) {
        this.statusId = statusId;
    }

    public Boolean getDiscountActive() {
        return discountActive;
    }

    public void setDiscountActive(Boolean discountActive) {
        this.discountActive = discountActive;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Address getAddress() {
        return address;
    }

    @Override
    protected String beginSql() {
        return BEGIN_SQL;
    }

    @Override
    protected String beginSqlCount() {
        return BEGIN_SQL_COUNT;
    }

    @Override
    protected StringBuilder appendWhereStatus(StringBuilder sql) {
        if (statusId != null) {
            appendWhere(sql);
            sql.append("p.status_id = :status_id ");
        }
        if (discountActive != null) {
            appendWhere(sql);
            sql.append("d.active = :active ");
        }
        return sql;
    }

    @Override
    protected StringBuilder appendWhereParam(StringBuilder sql) {
        if (address != null) {
            appendWhere(sql);
            sql.append(" group_id IN ( " +
                    "  SELECT group_id " +
                    "  FROM region_groups " +
                    "  WHERE region_id = :region_id " +
                    ")");
            appendWhere(sql);
            sql.append("p.id NOT IN ( " +
                    "SELECT o.product_id " +
                    "FROM orders o " +
                    "WHERE o.customer_id = :customer_id ) ");
        } else {
            if (customerId != null) {
                appendWhere(sql);
                sql.append("p.id IN ( " +
                        "SELECT o.product_id " +
                        "FROM orders o " +
                        "WHERE o.customer_id = :customer_id ) ");
            }
        }
        return sql;
    }
}
