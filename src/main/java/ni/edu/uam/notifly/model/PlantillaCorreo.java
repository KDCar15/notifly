package ni.edu.uam.notifly.model;

/** Lightweight view model for respuestas.plantillas. */
public final class PlantillaCorreo {
    private Integer id;
    private String nombre;
    private String contenido;

    public PlantillaCorreo(Integer id, String nombre, String contenido) {
        this.id = id;
        this.nombre = nombre;
        this.contenido = contenido;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getContenido() { return contenido; }
    public void setContenido(String contenido) { this.contenido = contenido; }

    @Override
    public String toString() { return nombre; }
}
