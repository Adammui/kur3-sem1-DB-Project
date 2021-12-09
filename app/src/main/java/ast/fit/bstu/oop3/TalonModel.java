package ast.fit.bstu.oop3;

public class TalonModel {

    private int id;
    private String name;
    private int doctor;
    private String town;
    private String time;
    private String an;

    public TalonModel(String name, String town, int doctor, String time, String an) {
        this.name = name;
        this.town = town;
        this.doctor = doctor;
        this.time = time;
        this.an = an;
    }

    public TalonModel() {
    }


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getTown() {
        return town;
    }

    public Integer getDoctor() {
        return doctor;
    }

    public String getTime() {
        return time;
    }

    public String getAn() {
        return an;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}

