package ast.fit.bstu.oop3;

public class ProfileModel {

    private int id;
    private String name;
    private String surname;

    public ProfileModel(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

    public ProfileModel() {
    }


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }


    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
