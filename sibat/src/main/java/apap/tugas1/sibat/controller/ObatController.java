package apap.tugas1.sibat.controller;

import apap.tugas1.sibat.model.*;
import apap.tugas1.sibat.service.GudangService;
import apap.tugas1.sibat.service.JenisService;
import apap.tugas1.sibat.service.ObatService;
import apap.tugas1.sibat.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ObatController {
    @Qualifier("obatServiceImpl")
    @Autowired
    private ObatService obatService;

    @Autowired
    private JenisService jenisService;

    @Autowired
    private SupplierService supplierService;

    @Autowired
    private GudangService gudangService;

    @RequestMapping("/")
    public String beranda(Model model) {
        List<ObatModel> listObat = obatService.getObatList();
        model.addAttribute("obatList", listObat);
        return "beranda";
    }

    // URL mapping untuk mengakses halaman tambah obat
    @RequestMapping(value = "/obat/tambah", method = RequestMethod.GET)
    public String addObatFormPage(Model model) {
        ObatModel obatBaru = new ObatModel();
        JenisModel jenisObat = new JenisModel();
        ObatSupplierModel obatSupplier = new ObatSupplierModel();
        obatBaru.setJenis(jenisObat);

        List<ObatSupplierModel> listObatSupplier = new ArrayList<>();
        List<JenisModel> listJenis = jenisService.getJenisList();
        List<SupplierModel> listSupplier = supplierService.getSupplierList();
        listObatSupplier.add(obatSupplier);
        obatBaru.setListObatSupplier(listObatSupplier);

        model.addAttribute("obat", obatBaru);
        model.addAttribute("jenisObat", jenisObat);
        model.addAttribute("jenisList", listJenis);
        model.addAttribute("supplierList", listSupplier);
        return "form-tambah-obat";
    }

    // URL mapping untuk submit form yang telah diisi user
    @RequestMapping(path = "/obat/", method = RequestMethod.POST)
    public String addObatSubmit(@ModelAttribute ObatModel obat, Model model) {
        for (ObatSupplierModel obatSupplier : obat.getListObatSupplier()) {
            obatSupplier.setObat(obat);
        }
        obatService.addObat(obat);
        model.addAttribute("namaObat", obat.getNama());
        model.addAttribute("kodeObat", obat.getKode());
        return "tambah-obat-sukses";
    }

    @RequestMapping(path = "/obat/view", method = RequestMethod.GET)
    public String viewObat(@RequestParam(value = "noReg") String nomorRegistrasi, Model model) {
        ObatModel obat = obatService.getObatByNomorRegistrasi(nomorRegistrasi).get();
        List<GudangObatModel> listGudangObat = obat.getListGudangObat();
        List<ObatSupplierModel> listSupplierObat = obat.getListObatSupplier();
        model.addAttribute("obat", obat);
        model.addAttribute("gudangObatList", listGudangObat);
        model.addAttribute("supplierObatList", listSupplierObat);
        return "lihat-obat";
    }

    @RequestMapping(path = "/obat/ubah", method = RequestMethod.GET)
    public String changeObatFormPage(@RequestParam(value = "id") Long idObat, Model model) {
        ObatModel existingObat = obatService.getObatByIdObat(idObat).get();
        JenisModel jenisObat = new JenisModel();
        List<JenisModel> listJenis = jenisService.getJenisList();
        List<SupplierModel> listSupplier = supplierService.getSupplierList();
        model.addAttribute("obat", existingObat);
        model.addAttribute("jenisObat", jenisObat);
        model.addAttribute("jenisList", listJenis);
        model.addAttribute("supplierList", listSupplier);
        return "form-ubah-obat";
    }

    @RequestMapping(path = "/obat/ubah", method = RequestMethod.POST)
    public String changeObatFormSubmit(@RequestParam(value = "id") Long idObat, @ModelAttribute ObatModel obat, Model model) {
        ObatModel newObatData = obatService.ubahObat(obat);
        model.addAttribute("namaObat", newObatData.getNama());
        model.addAttribute("kodeObat", newObatData.getKode());
        return "ubah-obat-sukses";
    }

    @RequestMapping(value = "/obat/", params = {"addRow"}, method = RequestMethod.POST)
    public String addRow(ObatModel obat, ObatSupplierModel obatSupplier, BindingResult bindingResult, Model model) {
        if (obat.getListObatSupplier() == null) {
            obat.setListObatSupplier(new ArrayList<ObatSupplierModel>());
        }
        obat.getListObatSupplier().add(obatSupplier);

        List<JenisModel> listJenis = jenisService.getJenisList();
        List<SupplierModel> listSupplier = supplierService.getSupplierList();

        model.addAttribute("obat", obat);
        model.addAttribute("jenisList", listJenis);
        model.addAttribute("supplierList", listSupplier);
        return "form-tambah-obat";
    }

    @RequestMapping(path = "/obat/filter", method = RequestMethod.GET)
    public String filterObat(@RequestParam(value = "idGudang", required = false) Long idGudang,
                             @RequestParam(value = "idSupplier", required = false) Long idSupplier,
                             @RequestParam(value = "idJenis", required = false) Long idJenis,
                             Model model) {
        List<GudangModel> listGudang = gudangService.getGudangList();
        List<JenisModel> listJenis = jenisService.getJenisList();
        List<SupplierModel> listSupplier = supplierService.getSupplierList();

        List<ObatModel> listObat = obatService.getObatList();
        if (idGudang != null) {
            List<GudangObatModel> listGudangObat = gudangService.getGudangByIdGudang(idGudang).get().getListGudangObat();
            List<ObatModel> listObatByGudang = new ArrayList<>();
            // Menambahkan obat berdasarkan id gudang
            for (GudangObatModel gudangObat : listGudangObat) {
                listObatByGudang.add(gudangObat.getObat());
            }
            // Menambahkan obat yang ada di gudang ke list
            List<ObatModel> temp = new ArrayList<>();
            for (ObatModel obat : listObat) {
                if (listObatByGudang.contains(obat)) {
                    temp.add(obat);
                }
            }
            // Mengganti list yang berisi semua obat menjadi obat yang ada di suatu gudang berdasarkan id
            listObat = temp;
            GudangModel gudang = gudangService.getGudangByIdGudang(idGudang).get();
            model.addAttribute("namaGudang", gudang.getNama());
        }

        if (idSupplier != null) {
            List<ObatSupplierModel> listObatSupplier = supplierService.getSupplierByIdSupplier(idSupplier).get().getListObatSupplier();
            List<ObatModel> listObatBySupplier = new ArrayList<>();
            // Menambahkan obat berdasarkan id supplier
            for (ObatSupplierModel obatSupplier : listObatSupplier) {
                listObatBySupplier.add(obatSupplier.getObat());
            }
            // Menambahkan obat yang ada di supplier ke list
            List<ObatModel> temp2 = new ArrayList<>();
            for (ObatModel obat : listObat) {
                if (listObatBySupplier.contains(obat)) {
                    temp2.add(obat);
                }
            }
            // Mengganti list yang berisi semua obat menjadi obat yang ada di suatu supplier berdasarkan id
            listObat = temp2;
            SupplierModel supplier = supplierService.getSupplierByIdSupplier(idSupplier).get();
            model.addAttribute("namaSupplier", supplier.getNama());
        }

        if (idJenis != null) {
            JenisModel jenis = jenisService.getJenisByIdJenis(idJenis).get();
            List<ObatModel> listObatTemp = new ArrayList<>(listObat);
            listObat = new ArrayList<>();
            for (ObatModel obat : listObatTemp) {
                if (jenis == obat.getJenis()) {
                    listObat.add(obat);
                }
            }
        }

        model.addAttribute("gudangList", listGudang);
        model.addAttribute("jenisList", listJenis);
        model.addAttribute("supplierList", listSupplier);
        model.addAttribute("dataFilter", listObat);
        model.addAttribute("jumlahObat", listObat.size());
        return "filter-obat";
    }
}
