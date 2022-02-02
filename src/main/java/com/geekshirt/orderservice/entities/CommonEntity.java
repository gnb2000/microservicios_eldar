package com.geekshirt.orderservice.entities;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.Date;

@Data
@EntityListeners({AuditingEntityListener.class})
@MappedSuperclass //Define que esta clase sera una clase base por lo tanto, los campos de esta, se heredan a los hijos, ESTO SE APLICARA A TODAS LAS ENTIDADES
public class CommonEntity implements Serializable {

    @Column(name = "CREATED_DATE")
    @CreatedDate
    private Date createdDate;

    @Column(name = "LAST_UPDATE_DATE")
    @LastModifiedDate
    private Date lastUpdateDate;





}
