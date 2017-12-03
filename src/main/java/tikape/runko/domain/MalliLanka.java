package tikape.runko.domain;

public class MalliLanka {

    private Integer malli_id;
    private Integer lanka_id;
    private String maara;
    


    public MalliLanka(Integer malli_id, Integer lanka_id, String maara) {
        this.malli_id = malli_id;
        this.lanka_id = lanka_id;
        this.maara = maara;
    }

    public Integer getMalliId() {
        return malli_id;
    }
    
    public Integer getLankaId() {
        return lanka_id;
    }
    
    public String getMaara() {
        return maara;
    }

}