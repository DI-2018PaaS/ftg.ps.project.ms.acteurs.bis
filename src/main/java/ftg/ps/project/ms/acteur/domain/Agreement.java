package ftg.ps.project.ms.acteur.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Agreement.
 */
@Entity
@Table(name = "agreement")
@Document(indexName = "agreement")
public class Agreement implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "agreement_id")
    private Long agreementId;

    @Column(name = "numero_agrement")
    private String numeroAgrement;

    @Column(name = "date_attibution")
    private LocalDate dateAttibution;

    @Column(name = "date_deb_validite")
    private LocalDate dateDebValidite;

    @Column(name = "date_fin_validite")
    private LocalDate dateFinValidite;

    @Column(name = "status")
    private Boolean status;

    @Column(name = "user_created")
    private Long userCreated;

    @Column(name = "user_last_modif")
    private Long userLastModif;

    @Column(name = "date_created")
    private LocalDate dateCreated;

    @Column(name = "date_last_modif")
    private LocalDate dateLastModif;

    @Column(name = "niveau_agreement")
    private Integer niveauAgreement;

    @OneToMany(mappedBy = "agreement")
    private Set<Fournisseur> agFournisseurs = new HashSet<>();
    @OneToMany(mappedBy = "agreement")
    private Set<Animateur> agAnimateurs = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAgreementId() {
        return agreementId;
    }

    public Agreement agreementId(Long agreementId) {
        this.agreementId = agreementId;
        return this;
    }

    public void setAgreementId(Long agreementId) {
        this.agreementId = agreementId;
    }

    public String getNumeroAgrement() {
        return numeroAgrement;
    }

    public Agreement numeroAgrement(String numeroAgrement) {
        this.numeroAgrement = numeroAgrement;
        return this;
    }

    public void setNumeroAgrement(String numeroAgrement) {
        this.numeroAgrement = numeroAgrement;
    }

    public LocalDate getDateAttibution() {
        return dateAttibution;
    }

    public Agreement dateAttibution(LocalDate dateAttibution) {
        this.dateAttibution = dateAttibution;
        return this;
    }

    public void setDateAttibution(LocalDate dateAttibution) {
        this.dateAttibution = dateAttibution;
    }

    public LocalDate getDateDebValidite() {
        return dateDebValidite;
    }

    public Agreement dateDebValidite(LocalDate dateDebValidite) {
        this.dateDebValidite = dateDebValidite;
        return this;
    }

    public void setDateDebValidite(LocalDate dateDebValidite) {
        this.dateDebValidite = dateDebValidite;
    }

    public LocalDate getDateFinValidite() {
        return dateFinValidite;
    }

    public Agreement dateFinValidite(LocalDate dateFinValidite) {
        this.dateFinValidite = dateFinValidite;
        return this;
    }

    public void setDateFinValidite(LocalDate dateFinValidite) {
        this.dateFinValidite = dateFinValidite;
    }

    public Boolean isStatus() {
        return status;
    }

    public Agreement status(Boolean status) {
        this.status = status;
        return this;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Long getUserCreated() {
        return userCreated;
    }

    public Agreement userCreated(Long userCreated) {
        this.userCreated = userCreated;
        return this;
    }

    public void setUserCreated(Long userCreated) {
        this.userCreated = userCreated;
    }

    public Long getUserLastModif() {
        return userLastModif;
    }

    public Agreement userLastModif(Long userLastModif) {
        this.userLastModif = userLastModif;
        return this;
    }

    public void setUserLastModif(Long userLastModif) {
        this.userLastModif = userLastModif;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public Agreement dateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public LocalDate getDateLastModif() {
        return dateLastModif;
    }

    public Agreement dateLastModif(LocalDate dateLastModif) {
        this.dateLastModif = dateLastModif;
        return this;
    }

    public void setDateLastModif(LocalDate dateLastModif) {
        this.dateLastModif = dateLastModif;
    }

    public Integer getNiveauAgreement() {
        return niveauAgreement;
    }

    public Agreement niveauAgreement(Integer niveauAgreement) {
        this.niveauAgreement = niveauAgreement;
        return this;
    }

    public void setNiveauAgreement(Integer niveauAgreement) {
        this.niveauAgreement = niveauAgreement;
    }

    public Set<Fournisseur> getAgFournisseurs() {
        return agFournisseurs;
    }

    public Agreement agFournisseurs(Set<Fournisseur> fournisseurs) {
        this.agFournisseurs = fournisseurs;
        return this;
    }

    public Agreement addAgFournisseur(Fournisseur fournisseur) {
        this.agFournisseurs.add(fournisseur);
        fournisseur.setAgreement(this);
        return this;
    }

    public Agreement removeAgFournisseur(Fournisseur fournisseur) {
        this.agFournisseurs.remove(fournisseur);
        fournisseur.setAgreement(null);
        return this;
    }

    public void setAgFournisseurs(Set<Fournisseur> fournisseurs) {
        this.agFournisseurs = fournisseurs;
    }

    public Set<Animateur> getAgAnimateurs() {
        return agAnimateurs;
    }

    public Agreement agAnimateurs(Set<Animateur> animateurs) {
        this.agAnimateurs = animateurs;
        return this;
    }

    public Agreement addAgAnimateur(Animateur animateur) {
        this.agAnimateurs.add(animateur);
        animateur.setAgreement(this);
        return this;
    }

    public Agreement removeAgAnimateur(Animateur animateur) {
        this.agAnimateurs.remove(animateur);
        animateur.setAgreement(null);
        return this;
    }

    public void setAgAnimateurs(Set<Animateur> animateurs) {
        this.agAnimateurs = animateurs;
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
        Agreement agreement = (Agreement) o;
        if (agreement.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), agreement.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Agreement{" +
            "id=" + getId() +
            ", agreementId=" + getAgreementId() +
            ", numeroAgrement='" + getNumeroAgrement() + "'" +
            ", dateAttibution='" + getDateAttibution() + "'" +
            ", dateDebValidite='" + getDateDebValidite() + "'" +
            ", dateFinValidite='" + getDateFinValidite() + "'" +
            ", status='" + isStatus() + "'" +
            ", userCreated=" + getUserCreated() +
            ", userLastModif=" + getUserLastModif() +
            ", dateCreated='" + getDateCreated() + "'" +
            ", dateLastModif='" + getDateLastModif() + "'" +
            ", niveauAgreement=" + getNiveauAgreement() +
            "}";
    }
}
