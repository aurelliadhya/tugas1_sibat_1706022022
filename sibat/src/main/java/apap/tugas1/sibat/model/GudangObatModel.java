package apap.tugas1.sibat.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "gudangObat")
public class GudangObatModel implements Serializable {
    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idGudangObat;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "gudangId", referencedColumnName = "idGudang", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private GudangModel gudang;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "obatId", referencedColumnName = "idObat", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private ObatModel obat;
}
