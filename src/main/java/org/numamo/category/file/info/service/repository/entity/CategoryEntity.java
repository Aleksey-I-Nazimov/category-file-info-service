package org.numamo.category.file.info.service.repository.entity;


import org.numamo.category.file.info.service.repository.entity.basics.BasicEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="category")
public final class CategoryEntity extends BasicEntity {

    @Column(name="code",nullable=false,unique=true)
    private String code;

    @Column(name="name",nullable=false,unique=true)
    private String name;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "CategoryEntity{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", id=" + id +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
