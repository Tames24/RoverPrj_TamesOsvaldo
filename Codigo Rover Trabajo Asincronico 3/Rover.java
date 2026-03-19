// Osvaldo Tames Cordero 2024801112
// Programacion orientada a objetos / Trabajo Asincronico #3

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;


public class Rover {

  //Atributos de identidad 
  private String codigoRover;
  private String nombrePropio;

  //Atributos de potencia 
  private double potenciaInicial;
  private double potenciaDisponible;
  private final double costoMovimiento;
  private final double costoDeteccion;
  private int cantidadRecargasRealizadas;
  private final int recargasMaximas;

  //Atributos de posición
  private final int posicionInicialX;
  private final int posicionInicialY;
  private int posicionActualX;
  private int posicionActualY;

  // Atributos de registro
  private int contadorDetecciones;
  private List<List<String>> mandatosExitosos;
  private List<List<String>> mandatosFallidos;

  //Contador estático de Rovers 
  private static int totalRoversCreados = 0;
  private static List<Rover> listaRovers = new ArrayList<>();
  
  // Constructores
  public Rover(String nombrePropioP) {
    this(nombrePropioP, 100.0);
  }

  public Rover(String nombrePropioP, double potenciaP) {
    this.nombrePropio = nombrePropioP;
    this.potenciaInicial = potenciaP;
    this.potenciaDisponible = potenciaP;
    this.costoMovimiento = 0.5;
    this.costoDeteccion = 0.25;
    this.recargasMaximas = 5;
    this.cantidadRecargasRealizadas = 0;
    this.contadorDetecciones = 0;
    this.posicionInicialX = 0;
    this.posicionInicialY = 0;
    this.posicionActualX = 0;
    this.posicionActualY = 0;
    this.mandatosExitosos = new ArrayList<>();
    this.mandatosFallidos = new ArrayList<>();
    this.codigoRover = "RVR-" + (System.currentTimeMillis() % 100000);

    totalRoversCreados++;
    listaRovers.add(this);
  }


  // Metodos de desplazamiento

  public void moverArriba() {
    ejecutarDesplazamiento("Desplazamiento Arriba", 0, 1);
  }

  public void moverAbajo() {
    ejecutarDesplazamiento("Desplazamiento Abajo", 0, -1);
  }
  
  public void moverDerecha() {
    ejecutarDesplazamiento("Desplazamiento Derecha", 1, 0);
  }

  public void moverIzquierda() {
    ejecutarDesplazamiento("Desplazamiento Izquierda", -1, 0);
  }

  //Metodos de consultas

  public String consultarPosicionActual() {
    return "Posición actual: (" + posicionActualX + ", " + posicionActualY + ")";
  }

  public double getPotenciaDisponible() {
    return potenciaDisponible;
  }

  public void recargarUnidadesPotencia(double cantidadP) {
    String tipoMandato = "Recarga (" + cantidadP + " unidades)";
    if (validarRecarga()) {
      potenciaDisponible += cantidadP;
      cantidadRecargasRealizadas++;
      registrarMandato(tipoMandato, "Posible");
    } else {
      registrarMandato(tipoMandato, "No posible: recargas máximas alcanzadas");
    }
  }

  public static int getTotalRoversCreados() {
    return totalRoversCreados;
  }

  public static String getInformacionTodosLosRovers() {
    StringBuilder sb = new StringBuilder();
    sb.append("  FLOTA MARCIANA — Total: ").append(totalRoversCreados).append(" Rover(s)\n");
    for (Rover r : listaRovers) {
      sb.append(r.toString()).append("\n");
    }
    return sb.toString();
  }

  private void ejecutarDesplazamiento(String tipoMandatoP, int deltaXP, int deltaYP) {
    if (!validarPotenciaActual()) {
      registrarMandato(tipoMandatoP, "No es posible: potencia insuficiente");
      return;
    }
    if (detectarFuga()) {
      registrarMandato(tipoMandatoP, "No es posible: fuga de calor detectada");
      return;
    }
    posicionActualX += deltaXP;
    posicionActualY += deltaYP;
    potenciaDisponible -= costoMovimiento;
    registrarMandato(tipoMandatoP, "Posible");
  }

  private boolean detectarFuga() {
    contadorDetecciones++;
    potenciaDisponible -= costoDeteccion;
    return new Random().nextDouble() >= 0.5;
  }

  private boolean validarPotenciaActual() {
    return potenciaDisponible >= (costoMovimiento + costoDeteccion);
  }

  private boolean validarRecarga() {
    return cantidadRecargasRealizadas < recargasMaximas;
  }

  private String determinarFechaHoraActual() {
    return new SimpleDateFormat("dd/MM/yy HH:mm:ss").format(new Date());
  }

  private void registrarMandato(String tipoMandatoP, String estatusMandatoP) {
    ArrayList<String> mandato = new ArrayList<>();
    mandato.add(tipoMandatoP);
    mandato.add(estatusMandatoP);
    mandato.add(determinarFechaHoraActual());

    if ("Posible".equals(estatusMandatoP)) {
      mandatosExitosos.add(mandato);
    } else {
      mandatosFallidos.add(mandato);
    }
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();


    sb.append(" -------- FICHA DEL ROVER ----------  \n");
    sb.append(String.format("  Código          : %s%n", codigoRover));
    sb.append(String.format("  Nombre          : %s%n", nombrePropio));
    sb.append(String.format("  Potencia inicial: %.2f unidades%n", potenciaInicial));
    sb.append(String.format("  Potencia actual : %.2f unidades%n", potenciaDisponible));
    sb.append(String.format("  Recargas        : %d / %d%n",
        cantidadRecargasRealizadas, recargasMaximas));
    sb.append(String.format("  Detecciones fuga: %d%n", contadorDetecciones));
    sb.append(String.format("  Posición inicial: (%d, %d)%n", posicionInicialX, posicionInicialY));
    sb.append(String.format("  Posición actual : (%d, %d)%n", posicionActualX, posicionActualY));

    sb.append("\n Mandatos EXITOSOS ─────────────────────\n");
    sb.append(String.format("  %-4s %-20s %-25s %-10s%n",
        "N°", "Fecha/Hora", "Tipo", "Estatus"));
    if (mandatosExitosos.isEmpty()) {
      sb.append("  (sin registros)\n");
    } else {
      for (int i = 0; i < mandatosExitosos.size(); i++) {
        List<String> m = mandatosExitosos.get(i);
        sb.append(String.format("  %-4d %-20s %-25s %-10s%n",
            (i + 1),
            m.size() > 2 ? m.get(2) : "",
            m.size() > 0 ? m.get(0) : "",
            m.size() > 1 ? m.get(1) : ""));
      }
    }

    sb.append("\n Mandatos FALLIDOS ─────────────────────\n");
    sb.append(String.format("  %-4s %-20s %-25s %-10s%n",
        "N°", "Fecha/Hora", "Tipo", "Estatus"));
    if (mandatosFallidos.isEmpty()) {
      sb.append("  (sin registros)\n");
    } else {
      for (int i = 0; i < mandatosFallidos.size(); i++) {
        List<String> m = mandatosFallidos.get(i);
        sb.append(String.format("  %-4d %-20s %-25s %-10s%n",
            (i + 1),
            m.size() > 2 ? m.get(2) : "",
            m.size() > 0 ? m.get(0) : "",
            m.size() > 1 ? m.get(1) : ""));
      }
    }

    return sb.toString();
  }
}