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
 * A Organisme.
 */
@Entity
@Table(name = "organisme")
@Document(indexName = "organisme")
public class Organisme implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "organisme_id")
    private Long organismeId;

    @Column(name = "raison_sociale")
    private String raisonSociale;

    @Column(name = "type_organisme")
    private String typeOrganisme;

    @Column(name = "domaine_activite")
    private String domaineActivite;

    @Column(name = "email")
    private String email;

    @Column(name = "telephone")
    private String telephone;

    @Column(name = "user_created")
    private Long userCreated;

    @Column(name = "user_last_modif")
    private Long userLastModif;

    @Column(name = "date_created")
    private LocalDate dateCreated;

    @Column(name = "date_last_modif")
    private LocalDate dateLastModif;

    @OneToOne    @JoinColumn(unique = true)
    private ActeurType acteurType;

    @OneToMany(mappedBy = "organisme")
    private Set<Contact> ocontacts = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrganismeId() {
        return organismeId;
    }

    public Organisme organismeId(Long organismeId) {
        this.organismeId = organismeId;
        return this;
    }

    public void setOrganismeId(Long organismeId) {
        this.organismeId = organismeId;
    }

    public String getRaisonSociale() {
        return raisonSociale;
    }

    public Organisme raisonSociale(String raisonSociale) {
        this.raisonSociale = raisonSociale;
        return this;
    }

    public void setRaisonSociale(String raisonSociale) {
        this.raisonSociale = raisonSociale;
    }

    public String getTypeOrganisme() {
        return typeOrganisme;
    }

    public Organisme typeOrganisme(String typeOrganisme) {
        this.typeOrganisme = typeOrganisme;
        return this;
    }

    public void setTypeOrganisme(String typeOrganisme) {
        this.typeOrganisme = typeOrganisme;
    }

    public String getDomaineActivite() {
        return domaineActivite;
    }

    public Organisme domaineActivite(String domaineActivite) {
        this.domaineActivite = domaineActivite;
        return this;
    }

    public void setDomaineActivite(String domaineActivite) {
        this.domaineActivite = domaineActivite;
    }

    public String getEmail() {
        return email;
    }

    public Organisme email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public Organisme telephone(String telephone) {
        this.telephone = telephone;
        return this;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Long getUserCreated() {
        return userCreated;
    }

    public Organisme userCreated(Long userCreated) {
        this.userCreated = userCreated;
        return this;
    }

    public void setUserCreated(Long userCreated) {
        this.userCreated = userCreated;
    }

    public Long getUserLastModif() {
        return userLastModif;
    }

    public Organisme userLastModif(Long userLastModif) {
        this.userLastModif = userLastModif;
        return this;
    }

    public void setUserLastModif(Long userLastModif) {
        this.userLastModif = userLastModif;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public Organisme dateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public LocalDate getDateLastModif() {
        return dateLastModif;
    }

    public Organisme dateLastModif(LocalDate dateLastModif) {
        this.dateLastModif = dateLastModif;
        return this;
    }

    public void setDateLastModif(LocalDate dateLastModif) {
        this.dateLastModif = dateLastModif;
    }

    public ActeurType getActeurType() {
        return acteurType;
    }

    public Organisme acteurType(ActeurType acteurType) {
        this.acteurType = acteurType;
        return this;
    }

    public void setActeurType(ActeurType acteurType) {
        this.acteurType = acteurType;
    }

    public Set<Contact> getOcontacts() {
        return ocontacts;
    }

    public Organisme ocontacts(Set<Contact> contacts) {
        this.ocontacts = contacts;
        return this;
    }

    public Organisme addOcontacts(Contact contact) {
        this.ocontacts.add(contact);
        contact.setOrganisme(this);
        return this;
    }

    public Organisme removeOcontacts(Contact contact) {
        this.ocontacts.remove(contact);
        contact.setOrganisme(null);
        return this;
    }

    public void setOcontacts(Set<Contact> contacts) {
        this.ocontacts = contacts;
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
        Organisme organisme = (Organisme) o;
        if (organisme.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), organisme.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Organisme{" +
            "id=" + getId() +
            ", organismeId=" + getOrganismeId() +
            ", raisonSociale='" + getRaisonSociale() + "'" +
            ", typeOrganisme='" + getTypeOrganisme() + "'" +
            ", domaineActivite='" + getDomaineActivite() + "'" +
            ", email='" + getEmail() + "'" +
            ", telephone='" + getTelephone() + "'" +
            ", userCreated=" + getUserCreated() +
            ", userLastModif=" + getUserLastModif() +
            ", dateCreated='" + getDateCreated() + "'" +
            ", dateLastModif='" + getDateLastModif() + "'" +
            "}";
    }
}
