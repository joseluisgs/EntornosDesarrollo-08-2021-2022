package model.vehiculos;

/**
 * Si no hacemos un modulo con provides, debemos indicar que esto es inject tambien, para ser inyectable
 */
public class Brand {
    private String name;

    // @Inject No lo usamos porque lo indicaremos en el Modulo
    public Brand(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Brand{" +
                "name='" + name + '\'' +
                '}';
    }
}
