package apap.tugas1.sibat.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "obat")
public class ObatModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idObat;

    @NotNull
    @Size(max = 255)
    @Column(name = "bentuk", nullable = false)
    private String bentuk;

    @NotNull
    @Column(name = "harga", nullable = false)
    private Double harga;

    @NotNull
    @Column(name = "kode", nullable = false)
    private String kode;

    @NotNull
    @Size(max = 255)
    @Column(name="nama", nullable = false)
    private String nama;

    @NotNull
    @Size(max = 255)
    @Column(name = "nomorRegistrasi", nullable = false, unique= true)
    private String nomorRegistrasi;

    @NotNull
    @Column(name = "tanggalTerbit", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date tanggalTerbit;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "jenisId", referencedColumnName = "idJenis", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private JenisModel jenis;

    @OneToMany(mappedBy = "obat", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<GudangObatModel> listGudangObat;

    @OneToMany(mappedBy = "obat", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ObatSupplierModel> listObatSupplier;

    public Long getIdObat() {
        return idObat;
    }

    public void setIdObat(Long idObat) {
        this.idObat = idObat;
    }

    public String getBentuk() {
        return bentuk;
    }

    public void setBentuk(String bentuk) {
        this.bentuk = bentuk;
    }

    public Double getHarga() {
        return harga;
    }

    public void setHarga(Double harga) {
        this.harga = harga;
    }

    public String getKode() {
        return kode;
    }

    public void setKode(String kode) {
        this.kode = kode;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNomorRegistrasi() {
        return nomorRegistrasi;
    }

    public void setNomorRegistrasi(String nomorRegistrasi) {
        this.nomorRegistrasi = nomorRegistrasi;
    }

    public Date getTanggalTerbit() {
        return tanggalTerbit;
    }

    public void setTanggalTerbit(Date tanggalTerbit) {
        this.tanggalTerbit = tanggalTerbit;
    }

    public JenisModel getJenis() {
        return jenis;
    }

    public void setJenis(JenisModel jenis) {
        this.jenis = jenis;
    }

    public List<GudangObatModel> getListGudangObat() {
        return listGudangObat;
    }

    public void setListGudangObat(List<GudangObatModel> listGudangObat) {
        this.listGudangObat = listGudangObat;
    }

    public List<ObatSupplierModel> getListObatSupplier() {
        return listObatSupplier;
    }

    public void setListObatSupplier(List<ObatSupplierModel> listObatSupplier) {
        this.listObatSupplier = listObatSupplier;
    }
}
