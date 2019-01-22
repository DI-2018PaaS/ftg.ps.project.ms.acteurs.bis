package ftg.ps.project.ms.acteur.domain;


import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A ActeurType.
 */
@Entity
@Table(name = "acteur_type")
@Document(indexName = "acteurtype")
public class ActeurType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "id_acteur_type")
    private Long idActeurType;

    @Column(name = "libelle_acteur")
    private String libelleActeur;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdActeurType() {
        return idActeurType;
    }

    public ActeurType idActeurType(Long idActeurType) {
        this.idActeurType = idActeurType;
        return this;
    }

    public void setIdActeurType(Long idActeurType) {
        this.idActeurType = idActeurType;
    }

    public String getLibelleActeur() {
        return libelleActeur;
    }

    public ActeurType libelleActeur(String libelleActeur) {
        this.libelleActeur = libelleActeur;
        return this;
    }

    public void setLibelleActeur(String libelleActeur) {
        this.libelleActeur = libelleActeur;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ActeurType acteurType = (ActeurType) o;
        if (acteurType.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), acteurType.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ActeurType{" +
            "id=" + getId() +
            ", idActeurType=" + getIdActeurType() +
            ", libelleActeur='" + getLibelleActeur() + "'" +
            "}";
    }
}
