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
 * A Acheteur.
 */
@Entity
@Table(name = "acheteur")
@Document(indexName = "acheteur")
public class Acheteur implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "acheteur_id")
    private Long acheteurId;

    @Column(name = "jhi_type")
    private String type;

    @Column(name = "nom")
    private String nom;

    @Column(name = "prenom")
    private String prenom;

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
    private Banque clientBanque;

    @OneToOne    @JoinColumn(unique = true)
    private Organisme clientOrga;

    @OneToOne    @JoinColumn(unique = true)
    private ActeurType acteurType;

    @OneToMany(mappedBy = "acheteur")
    private Set<Contact> acontacts = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAcheteurId() {
        return acheteurId;
    }

    public Acheteur acheteurId(Long acheteurId) {
        this.acheteurId = acheteurId;
        return this;
    }

    public void setAcheteurId(Long acheteurId) {
        this.acheteurId = acheteurId;
    }

    public String getType() {
        return type;
    }

    public Acheteur type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNom() {
        return nom;
    }

    public Acheteur nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public Acheteur prenom(String prenom) {
        this.prenom = prenom;
        return this;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public Acheteur email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public Acheteur telephone(String telephone) {
        this.telephone = telephone;
        return this;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Long getUserCreated() {
        return userCreated;
    }

    public Acheteur userCreated(Long userCreated) {
        this.userCreated = userCreated;
        return this;
    }

    public void setUserCreated(Long userCreated) {
        this.userCreated = userCreated;
    }

    public Long getUserLastModif() {
        return userLastModif;
    }

    public Acheteur userLastModif(Long userLastModif) {
        this.userLastModif = userLastModif;
        return this;
    }

    public void setUserLastModif(Long userLastModif) {
        this.userLastModif = userLastModif;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public Acheteur dateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public LocalDate getDateLastModif() {
        return dateLastModif;
    }

    public Acheteur dateLastModif(LocalDate dateLastModif) {
        this.dateLastModif = dateLastModif;
        return this;
    }

    public void setDateLastModif(LocalDate dateLastModif) {
        this.dateLastModif = dateLastModif;
    }

    public Banque getClientBanque() {
        return clientBanque;
    }

    public Acheteur clientBanque(Banque banque) {
        this.clientBanque = banque;
        return this;
    }

    public void setClientBanque(Banque banque) {
        this.clientBanque = banque;
    }

    public Organisme getClientOrga() {
        return clientOrga;
    }

    public Acheteur clientOrga(Organisme organisme) {
        this.clientOrga = organisme;
        return this;
    }

    public void setClientOrga(Organisme organisme) {
        this.clientOrga = organisme;
    }

    public ActeurType getActeurType() {
        return acteurType;
    }

    public Acheteur acteurType(ActeurType acteurType) {
        this.acteurType = acteurType;
        return this;
    }

    public void setActeurType(ActeurType acteurType) {
        this.acteurType = acteurType;
    }

    public Set<Contact> getAcontacts() {
        return acontacts;
    }

    public Acheteur acontacts(Set<Contact> contacts) {
        this.acontacts = contacts;
        return this;
    }

    public Acheteur addAcontacts(Contact contact) {
        this.acontacts.add(contact);
        contact.setAcheteur(this);
        return this;
    }

    public Acheteur removeAcontacts(Contact contact) {
        this.acontacts.remove(contact);
        contact.setAcheteur(null);
        return this;
    }

    public void setAcontacts(Set<Contact> contacts) {
        this.acontacts = contacts;
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
        Acheteur acheteur = (Acheteur) o;
        if (acheteur.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), acheteur.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Acheteur{" +
            "id=" + getId() +
            ", acheteurId=" + getAcheteurId() +
            ", type='" + getType() + "'" +
            ", nom='" + getNom() + "'" +
            ", prenom='" + getPrenom() + "'" +
            ", email='" + getEmail() + "'" +
            ", telephone='" + getTelephone() + "'" +
            ", userCreated=" + getUserCreated() +
            ", userLastModif=" + getUserLastModif() +
            ", dateCreated='" + getDateCreated() + "'" +
            ", dateLastModif='" + getDateLastModif() + "'" +
            "}";
    }
}
