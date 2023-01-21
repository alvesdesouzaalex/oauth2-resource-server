package br.com.zal.oauth2resourceserver.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "player")
public class Player {

    @Id
    private String id;
    private String name;
    private String email;
}
