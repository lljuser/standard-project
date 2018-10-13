package com.heyi.core.blockchain.repository.sql.entity;

import javax.persistence.*;

@Table(name = "bc_chain")
@Entity
public final class SqlBlockChain {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 当前链的标识
     */
    @Column(name = "identity_code", nullable = false)
    private String identityCode;

    /**
     * 当前链尾部块hash值
     */
    @Column(name = "last_hash", nullable = false)
    private String lastHash;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIdentityCode() {
        return identityCode;
    }

    public void setIdentityCode(String identityCode) {
        this.identityCode = identityCode;
    }

    public String getLastHash() {
        return lastHash;
    }

    public void setLastHash(String lastHash) {
        this.lastHash = lastHash;
    }
}
