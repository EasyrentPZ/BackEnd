package com.example.easyrent.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Embeddable
public class UserContractId implements Serializable
{
    private static final long serialVersionUID = -3476210301851758937L;
    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @Column(name = "contract_id", nullable = false)
    private Integer contractId;

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        UserContractId entity = (UserContractId) o;
        return Objects.equals(this.contractId, entity.contractId) &&
                Objects.equals(this.userId, entity.userId);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(contractId, userId);
    }

}