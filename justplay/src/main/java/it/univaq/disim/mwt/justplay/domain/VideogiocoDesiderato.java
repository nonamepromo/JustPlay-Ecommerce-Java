package it.univaq.disim.mwt.justplay.domain;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.validation.groups.Default;

import it.univaq.disim.mwt.justplay.presentation.validator.OnCreate;
import it.univaq.disim.mwt.justplay.presentation.validator.OnUpdate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "videogiochi_desiderati")
@Inheritance(strategy = InheritanceType.JOINED)
@NoArgsConstructor
@Getter
@Setter
@ToString
public class VideogiocoDesiderato extends AbstractPersistableEntity {

  @Column(name = "fk_utente")
  @NotNull(groups = { OnCreate.class, Default.class })
  private Long fkUtente;

  @Column(name = "fk_videogioco")
  @NotNull(groups = { OnCreate.class, Default.class })
  private Long fkVideogioco;
}