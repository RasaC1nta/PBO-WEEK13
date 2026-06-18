public class Mhs {
    private String nim, nama, huruf, predikat;
    private double uts, uas, tugas, akhir;

    public Mhs(String nim, String nama, double uts, double uas, double tugas) {
        this.nim = nim;
        this.nama = nama;
        this.uts = uts;
        this.uas = uas;
        this.tugas = tugas;
        hitungOtomatis();
    }

    private void hitungOtomatis() {
        this.akhir = (uts * 0.35) + (uas * 0.35) + (tugas * 0.30);
        
        if (akhir >= 85) { this.huruf = "A"; this.predikat = "Apik"; }
        else if (akhir >= 70) { this.huruf = "B"; this.predikat = "Baik"; }
        else if (akhir >= 55) { this.huruf = "C"; this.predikat = "Cukup"; }
        else { this.huruf = "D"; this.predikat = "Kurang"; }
    }
    
    public String getNim() { return nim; }
    public String getNama() { return nama; }
    public double getAkhir() { return akhir; }
    public String getHuruf() { return huruf; }
    public String getPredikat() { return predikat; }
}
