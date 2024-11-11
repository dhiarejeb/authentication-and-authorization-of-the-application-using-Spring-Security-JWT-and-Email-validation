package com.book.book_network.role;

import com.book.book_network.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@EntityListeners(AuditingEntityListener.class)
@Table(name = "role")
public class Role {

    @Id
    @GeneratedValue
    private Integer id;
    @Column(unique = true)
    private String name;

    @ManyToMany(mappedBy = "roles")
    @JsonIgnore //prevent it from getting serialized
    private List<User> users;



    @CreatedDate
    @Column(nullable = false,updatable = false)
    private LocalDateTime createdData;
    @LastModifiedDate
    @Column(insertable = false)//when creation it is not created for the first time because it can't be null
    private LocalDateTime lastModifiedData;
}
