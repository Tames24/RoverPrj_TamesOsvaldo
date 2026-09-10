import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
 
public class Cuenta {
 
    private String codCuenta;
    private double saldo;
    private String nombreCuentaHabiente;
    private String fechaCreacion;
    private int cantDepositosRealizados;
    private int cantRetirosExitososRealizados;
 
    private static int cantCuentasCreadas = 0;
 
    private static final String PREFIJO = "cta-";
 
    public Cuenta(String nombreCuentaHabiente, double pSaldo) {
        cantCuentasCreadas++;
        this.codCuenta = PREFIJO + cantCuentasCreadas;
        this.nombreCuentaHabiente = nombreCuentaHabiente;
        this.saldo = pSaldo;
        this.cantDepositosRealizados = 0;
        this.cantRetirosExitososRealizados = 0;
        this.fechaCreacion = establecerFechaCreacion();
    }
 
    
    public Cuenta(double pSaldo) {
        cantCuentasCreadas++;
        this.codCuenta = PREFIJO + cantCuentasCreadas;
        this.nombreCuentaHabiente = "Sin asignar";
        this.saldo = pSaldo;
        this.cantDepositosRealizados = 0;
        this.cantRetirosExitososRealizados = 0;
        this.fechaCreacion = establecerFechaCreacion();
    }
 
    
    private String establecerFechaCreacion() {
        Date fecha = new Date(System.currentTimeMillis());
        DateFormat formato = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return formato.format(fecha);
    }
 
    
    public void setNombreCuentaHabiente(String pNombreCuentaHabiente) {
        this.nombreCuentaHabiente = pNombreCuentaHabiente;
    }
 
    
    public String getCodCuenta() {
        return codCuenta;
    }
 
    
    public double getSaldo() {
        return saldo;
    }
 
    
    public double depositar(double monto) {
        if (monto > 0) {
            saldo += monto;
            cantDepositosRealizados++;
        } else {
            System.out.println("El monto a depositar debe ser mayor a cero.");
        }
        return saldo;
    }
 
    
    public double retirar(double monto) {
        if (monto <= 0) {
            System.out.println("El monto a retirar debe ser mayor a cero.");
            return saldo;
        }
        if (validarRetiro(monto)) {
            saldo -= monto;
            cantRetirosExitososRealizados++;
        } else {
            System.out.println("Fondos insuficientes. El retiro no pudo realizarse.");
        }
        return saldo;
    }
 
    private boolean validarRetiro(double monto) {
        return saldo >= monto;
    }
 
    public static int getCantCuentasCreadas() {
        return cantCuentasCreadas;
    }
 
    public String toString() {
        return "========== Estado de Cuenta ==========\n" +
               "Código:           " + codCuenta + "\n" +
               "Cuenta habiente:  " + nombreCuentaHabiente + "\n" +
               "Saldo:            " + String.format("%.2f", saldo) + "\n" +
               "Fecha creación:   " + fechaCreacion + "\n" +
               "Depósitos:        " + cantDepositosRealizados + "\n" +
               "Retiros exitosos: " + cantRetirosExitososRealizados + "\n" +
               "======================================";
    }
}