package crudproject;

import java.math.BigDecimal;

public class Adresses {
	private int ID;
	private String AdresKodu;
	private String Adi;
	private String Adresi;
	private String Semt;
	private String Sehir;
	private String Telefon;
	private BigDecimal BorcTutar;
	private BigDecimal AlacakTutar;
	private BigDecimal Bakiye;
	private String Notu;
	private String OzelAlanGrup;
	

	public Adresses(String adresKodu, String adi, String adresi, String semt, String sehir, String telefon,
			BigDecimal borcTutar, BigDecimal alacakTutar, BigDecimal bakiye, String notu, String ozelAlanGrup) {
		super();	
		AdresKodu = adresKodu;
		Adi = adi;
		Adresi = adresi;
		Semt = semt;
		Sehir = sehir;
		Telefon = telefon;
		BorcTutar = borcTutar;
		AlacakTutar = alacakTutar;
		Bakiye = bakiye;
		Notu = notu;
		OzelAlanGrup = ozelAlanGrup;
	}
	
	public Adresses(int id, String adresKodu, String adi, String adresi, String semt, String sehir,
	        String telefon, BigDecimal borcTutar, BigDecimal alacakTutar, BigDecimal bakiye,
	        String notu, String ozelAlanGrup) {
		this(adresKodu, adi, adresi, semt, sehir, telefon, borcTutar, alacakTutar, bakiye, notu, ozelAlanGrup);
	    this.ID = id;

	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String getAdresKodu() {
		return AdresKodu;
	}

	public void setAdresKodu(String adresKodu) {
		AdresKodu = adresKodu;
	}

	public String getAdi() {
		return Adi;
	}

	public void setAdi(String adi) {
		Adi = adi;
	}

	public String getAdresi() {
		return Adresi;
	}

	public void setAdresi(String adresi) {
		Adresi = adresi;
	}

	public String getSemt() {
		return Semt;
	}

	public void setSemt(String semt) {
		Semt = semt;
	}

	public String getSehir() {
		return Sehir;
	}

	public void setSehir(String sehir) {
		Sehir = sehir;
	}

	public String getTelefon() {
		return Telefon;
	}

	public void setTelefon(String telefon) {
		Telefon = telefon;
	}

	public BigDecimal getBorcTutar() {
		return BorcTutar;
	}

	public void setBorcTutar(BigDecimal borcTutar) {
		BorcTutar = borcTutar;
	}

	public BigDecimal getAlacakTutar() {
		return AlacakTutar;
	}

	public void setAlacakTutar(BigDecimal alacakTutar) {
		AlacakTutar = alacakTutar;
	}

	public BigDecimal getBakiye() {
		return Bakiye;
	}

	public void setBakiye(BigDecimal bakiye) {
		Bakiye = bakiye;
	}

	public String getNotu() {
		return Notu;
	}

	public void setNotu(String notu) {
		Notu = notu;
	}

	public String getOzelAlanGrup() {
		return OzelAlanGrup;
	}

	public void setOzelAlanGrup(String ozelAlanGrup) {
		OzelAlanGrup = ozelAlanGrup;
	}
	
	
}
