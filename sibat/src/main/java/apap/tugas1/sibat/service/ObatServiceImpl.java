package apap.tugas1.sibat.service;

import apap.tugas1.sibat.model.ObatModel;
import apap.tugas1.sibat.repository.ObatDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;

@Service
public class ObatServiceImpl implements ObatService {
    @Autowired
    private ObatDb obatDb;

    @Override
    public List<ObatModel> getObatList() {
        return obatDb.findAll();
    }

    @Override
    public void addObat(ObatModel obat) {
        generateCode(obat);
        obatDb.save(obat);
    }

    public void generateCode(ObatModel obat) {
        String kode = "";
        if (obat.getJenis().getNama().equals("Generik")) {
            kode += "1";
        } else {
            kode += "2";
        }

        if (obat.getBentuk().equals("Cairan")) {
            kode += "01";
        } else if (obat.getBentuk().equals("Kapsul")) {
            kode += "02";
        } else {
            kode += "03";
        }

        SimpleDateFormat formater = new SimpleDateFormat("yyyy");
        String tahunTerbit = formater.format(obat.getTanggalTerbit());
        Integer tahunPlusLima = Integer.valueOf(tahunTerbit) + 5;

        kode += tahunTerbit + String.valueOf(tahunPlusLima);
        kode += getRandomString(2);
        obat.setKode(kode);
    }

    public String getRandomString(int n) {
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder sb = new StringBuilder(n);
        for (int i=0;i<n;i++) {
            int index = (int) (alphabet.length() * Math.random());
            sb.append(alphabet.charAt(index));
        }
        return sb.toString();
    }

    @Override
    public Optional<ObatModel> getObatByNomorRegistrasi(String nomorRegistrasi) {
        return obatDb.findByNomorRegistrasi(nomorRegistrasi);
    }

    @Override
    public Optional<ObatModel> getObatByIdObat(Long idObat) {
        return obatDb.findByIdObat(idObat);
    }

    @Override
    public ObatModel ubahObat(ObatModel obatModel) {
        ObatModel targetObat = obatDb.findById(obatModel.getIdObat()).get();

        targetObat.setNama(obatModel.getNama());
        targetObat.setTanggalTerbit(obatModel.getTanggalTerbit());
        targetObat.setHarga(obatModel.getHarga());
        targetObat.setBentuk(obatModel.getBentuk());
        generateCode(targetObat);
        obatDb.save(targetObat);
        return targetObat;
    }
}
