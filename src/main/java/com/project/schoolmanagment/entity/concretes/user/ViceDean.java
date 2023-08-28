package com.project.schoolmanagment.entity.concretes.user;

import com.project.schoolmanagment.entity.abstracts.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;

@Entity
@Setter
@Getter
@NoArgsConstructor
@SuperBuilder
public class ViceDean extends User {
}
