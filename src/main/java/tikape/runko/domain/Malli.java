package tikape.runko.domain;

public class Malli {

    private Integer id;
    private String nimi;
    private String tyyppi;
    private String koko;
    private String ohje;


    public Malli(Integer id, String nimi, String tyyppi, String koko, String ohje) {
        this.id = id;
        this.nimi = nimi;
        this.tyyppi = tyyppi;
        this.ohje = ohje;
        this.koko = koko;
    }

    public Integer getId() {
        return id;
    }

    public String getTyyppi() {
        return tyyppi;
    }
    
    public String getNimi() {
        return nimi;
    }
    
    public String getKoko() {
        return koko;
    }
    

       public String getOhje() {
        return ohje;
    }
}
