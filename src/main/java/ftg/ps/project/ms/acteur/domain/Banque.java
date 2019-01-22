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
 * A Banque.
 */
@Entity
@Table(name = "banque")
@Document(indexName = "banque")
public class Banque implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "banque_id")
    private Long banqueId;

    @Column(name = "raison_sociale")
    private String raisonSociale;

    @Column(name = "email")
    private String email;

    @Column(name = "site_web")
    private String siteWeb;

    @Column(name = "telephone")
    private String telephone;

    @Column(name = "capital")
    private Long capital;

    @Column(name = "chiffre_daffaire")
    private Long chiffreDaffaire;

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

    @OneToMany(mappedBy = "banque")
    private Set<Contact> bcontacts = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBanqueId() {
        return banqueId;
    }

    public Banque banqueId(Long banqueId) {
        this.banqueId = banqueId;
        return this;
    }

    public void setBanqueId(Long banqueId) {
        this.banqueId = banqueId;
    }

    public String getRaisonSociale() {
        return raisonSociale;
    }

    public Banque raisonSociale(String raisonSociale) {
        this.raisonSociale = raisonSociale;
        return this;
    }

    public void setRaisonSociale(String raisonSociale) {
        this.raisonSociale = raisonSociale;
    }

    public String getEmail() {
        return email;
    }

    public Banque email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSiteWeb() {
        return siteWeb;
    }

    public Banque siteWeb(String siteWeb) {
        this.siteWeb = siteWeb;
        return this;
    }

    public void setSiteWeb(String siteWeb) {
        this.siteWeb = siteWeb;
    }

    public String getTelephone() {
        return telephone;
    }

    public Banque telephone(String telephone) {
        this.telephone = telephone;
        return this;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Long getCapital() {
        return capital;
    }

    public Banque capital(Long capital) {
        this.capital = capital;
        return this;
    }

    public void setCapital(Long capital) {
        this.capital = capital;
    }

    public Long getChiffreDaffaire() {
        return chiffreDaffaire;
    }

    public Banque chiffreDaffaire(Long chiffreDaffaire) {
        this.chiffreDaffaire = chiffreDaffaire;
        return this;
    }

    public void setChiffreDaffaire(Long chiffreDaffaire) {
        this.chiffreDaffaire = chiffreDaffaire;
    }

    public Long getUserCreated() {
        return userCreated;
    }

    public Banque userCreated(Long userCreated) {
        this.userCreated = userCreated;
        return this;
    }

    public void setUserCreated(Long userCreated) {
        this.userCreated = userCreated;
    }

    public Long getUserLastModif() {
        return userLastModif;
    }

    public Banque userLastModif(Long userLastModif) {
        this.userLastModif = userLastModif;
        return this;
    }

    public void setUserLastModif(Long userLastModif) {
        this.userLastModif = userLastModif;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public Banque dateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public LocalDate getDateLastModif() {
        return dateLastModif;
    }

    public Banque dateLastModif(LocalDate dateLastModif) {
        this.dateLastModif = dateLastModif;
        return this;
    }

    public void setDateLastModif(LocalDate dateLastModif) {
        this.dateLastModif = dateLastModif;
    }

    public ActeurType getActeurType() {
        return acteurType;
    }

    public Banque acteurType(ActeurType acteurType) {
        this.acteurType = acteurType;
        return this;
    }

    public void setActeurType(ActeurType acteurType) {
        this.acteurType = acteurType;
    }

    public Set<Contact> getBcontacts() {
        return bcontacts;
    }

    public Banque bcontacts(Set<Contact> contacts) {
        this.bcontacts = contacts;
        return this;
    }

    public Banque addBcontacts(Contact contact) {
        this.bcontacts.add(contact);
        contact.setBanque(this);
        return this;
    }

    public Banque removeBcontacts(Contact contact) {
        this.bcontacts.remove(contact);
        contact.setBanque(null);
        return this;
    }

    public void setBcontacts(Set<Contact> contacts) {
        this.bcontacts = contacts;
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
        Banque banque = (Banque) o;
        if (banque.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), banque.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Banque{" +
            "id=" + getId() +
            ", banqueId=" + getBanqueId() +
            ", raisonSociale='" + getRaisonSociale() + "'" +
            ", email='" + getEmail() + "'" +
            ", siteWeb='" + getSiteWeb() + "'" +
            ", telephone='" + getTelephone() + "'" +
            ", capital=" + getCapital() +
            ", chiffreDaffaire=" + getChiffreDaffaire() +
            ", userCreated=" + getUserCreated() +
            ", userLastModif=" + getUserLastModif() +
            ", dateCreated='" + getDateCreated() + "'" +
            ", dateLastModif='" + getDateLastModif() + "'" +
            "}";
    }
}
