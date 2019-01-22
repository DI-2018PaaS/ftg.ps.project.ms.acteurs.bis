package ftg.ps.project.ms.acteur.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Fournisseur.
 */
@Entity
@Table(name = "fournisseur")
@Document(indexName = "fournisseur")
public class Fournisseur implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "fournisseur_id")
    private Long fournisseurId;

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

    @ManyToOne
    @JsonIgnoreProperties("agrees")
    private Animateur animateur;

    @OneToOne    @JoinColumn(unique = true)
    private Banque foclientBanque;

    @OneToOne    @JoinColumn(unique = true)
    private Organisme foclientOrga;

    @OneToOne    @JoinColumn(unique = true)
    private ActeurType acteurType;

    @OneToMany(mappedBy = "fournisseur")
    private Set<Contact> fcontacts = new HashSet<>();
    @OneToMany(mappedBy = "fournisseur")
    private Set<Animateur> anims = new HashSet<>();
    @ManyToOne
    @JsonIgnoreProperties("agFournisseurs")
    private Agreement agreement;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFournisseurId() {
        return fournisseurId;
    }

    public Fournisseur fournisseurId(Long fournisseurId) {
        this.fournisseurId = fournisseurId;
        return this;
    }

    public void setFournisseurId(Long fournisseurId) {
        this.fournisseurId = fournisseurId;
    }

    public String getType() {
        return type;
    }

    public Fournisseur type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNom() {
        return nom;
    }

    public Fournisseur nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public Fournisseur prenom(String prenom) {
        this.prenom = prenom;
        return this;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public Fournisseur email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public Fournisseur telephone(String telephone) {
        this.telephone = telephone;
        return this;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Long getUserCreated() {
        return userCreated;
    }

    public Fournisseur userCreated(Long userCreated) {
        this.userCreated = userCreated;
        return this;
    }

    public void setUserCreated(Long userCreated) {
        this.userCreated = userCreated;
    }

    public Long getUserLastModif() {
        return userLastModif;
    }

    public Fournisseur userLastModif(Long userLastModif) {
        this.userLastModif = userLastModif;
        return this;
    }

    public void setUserLastModif(Long userLastModif) {
        this.userLastModif = userLastModif;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public Fournisseur dateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public LocalDate getDateLastModif() {
        return dateLastModif;
    }

    public Fournisseur dateLastModif(LocalDate dateLastModif) {
        this.dateLastModif = dateLastModif;
        return this;
    }

    public void setDateLastModif(LocalDate dateLastModif) {
        this.dateLastModif = dateLastModif;
    }

    public Animateur getAnimateur() {
        return animateur;
    }

    public Fournisseur animateur(Animateur animateur) {
        this.animateur = animateur;
        return this;
    }

    public void setAnimateur(Animateur animateur) {
        this.animateur = animateur;
    }

    public Banque getFoclientBanque() {
        return foclientBanque;
    }

    public Fournisseur foclientBanque(Banque banque) {
        this.foclientBanque = banque;
        return this;
    }

    public void setFoclientBanque(Banque banque) {
        this.foclientBanque = banque;
    }

    public Organisme getFoclientOrga() {
        return foclientOrga;
    }

    public Fournisseur foclientOrga(Organisme organisme) {
        this.foclientOrga = organisme;
        return this;
    }

    public void setFoclientOrga(Organisme organisme) {
        this.foclientOrga = organisme;
    }

    public ActeurType getActeurType() {
        return acteurType;
    }

    public Fournisseur acteurType(ActeurType acteurType) {
        this.acteurType = acteurType;
        return this;
    }

    public void setActeurType(ActeurType acteurType) {
        this.acteurType = acteurType;
    }

    public Set<Contact> getFcontacts() {
        return fcontacts;
    }

    public Fournisseur fcontacts(Set<Contact> contacts) {
        this.fcontacts = contacts;
        return this;
    }

    public Fournisseur addFcontacts(Contact contact) {
        this.fcontacts.add(contact);
        contact.setFournisseur(this);
        return this;
    }

    public Fournisseur removeFcontacts(Contact contact) {
        this.fcontacts.remove(contact);
        contact.setFournisseur(null);
        return this;
    }

    public void setFcontacts(Set<Contact> contacts) {
        this.fcontacts = contacts;
    }

    public Set<Animateur> getAnims() {
        return anims;
    }

    public Fournisseur anims(Set<Animateur> animateurs) {
        this.anims = animateurs;
        return this;
    }

    public Fournisseur addAnim(Animateur animateur) {
        this.anims.add(animateur);
        animateur.setFournisseur(this);
        return this;
    }

    public Fournisseur removeAnim(Animateur animateur) {
        this.anims.remove(animateur);
        animateur.setFournisseur(null);
        return this;
    }

    public void setAnims(Set<Animateur> animateurs) {
        this.anims = animateurs;
    }

    public Agreement getAgreement() {
        return agreement;
    }

    public Fournisseur agreement(Agreement agreement) {
        this.agreement = agreement;
        return this;
    }

    public void setAgreement(Agreement agreement) {
        this.agreement = agreement;
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
        Fournisseur fournisseur = (Fournisseur) o;
        if (fournisseur.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), fournisseur.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Fournisseur{" +
            "id=" + getId() +
            ", fournisseurId=" + getFournisseurId() +
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
