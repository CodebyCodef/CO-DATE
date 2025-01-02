package API;

public class Register {
    private String Email;
    private String Sifre; // password yerine
    private String Isim; // name yerine
    private String Soyisim; // surname yerine
    private String Yas; // age yerine
    private String TelNo;
    private String Cinsiyet; // gender yerine
    private String Biyografi; // biography yerine
    private String ProfileFoto; // Base64 profil fotoğrafı (şu an null gönderilebilir)

    // Getter ve Setter metotları
    public String getEmail() { return Email; }
    public void setEmail(String email) { Email = email; }

    public String getSifre() { return Sifre; }
    public void setSifre(String sifre) { Sifre = sifre; }

    public String getIsim() { return Isim; }
    public void setIsim(String isim) { Isim = isim; }

    public String getSoyisim() { return Soyisim; }
    public void setSoyisim(String soyisim) { Soyisim = soyisim; }

    public String getYas() { return Yas; }
    public void setYas(String yas) { Yas = yas; }

    public String getTelNo() { return TelNo; }
    public void setTelNo(String telNo) { TelNo = telNo; }

    public String getCinsiyet() { return Cinsiyet; }
    public void setCinsiyet(String cinsiyet) { Cinsiyet = cinsiyet; }

    public String getBiyografi() { return Biyografi; }
    public void setBiyografi(String biyografi) { Biyografi = biyografi; }

    /*public String getProfileFoto() { return ProfileFoto; }
    public void setProfileFoto(String profileFoto) { ProfileFoto = profileFoto; }*/
}
