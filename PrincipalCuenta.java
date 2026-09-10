import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PrincipalCuenta {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<Cuenta> cuentas = new ArrayList<>();
        int actual = -1; 

        System.out.println("==========================================");
        System.out.println("   Sistema de Gestión de Cuentas");
        System.out.println("==========================================");

        boolean salir = false;
        while (!salir) {
            System.out.println("\n--- Menú Principal ---");
            System.out.println("1)  Crear cuenta");
            System.out.println("2)  Conocer cantidad de cuentas creadas");
            System.out.println("3)  Listar cuentas");
            System.out.println("4)  Seleccionar cuenta actual");
            System.out.println("5)  Asignar nombre del cuenta habiente");
            System.out.println("6)  Depositar");
            System.out.println("7)  Retirar");
            System.out.println("8)  Consultar saldo");
            System.out.println("9)  Consultar estado de la cuenta");
            System.out.println("0)  Salir");
            System.out.print("Opción: ");
            String op = sc.nextLine().trim();

            switch (op) {

                case "1": { 
                    System.out.println("\n¿Qué constructor desea usar?");
                    System.out.println("  1) Un parámetro  (solo saldo inicial)");
                    System.out.println("  2) Dos parámetros (nombre + saldo inicial)");
                    System.out.print("Elija: ");
                    String tipo = sc.nextLine().trim();

                    if (tipo.equals("1")) {
                        double saldo = leerDouble(sc, "Saldo inicial: ");
                        Cuenta c = new Cuenta(saldo);
                        cuentas.add(c);
                        actual = cuentas.size() - 1;
                        System.out.println("Cuenta creada con código: " + c.getCodCuenta());
                        System.out.println("Recuerde asignar el nombre del cuenta habiente (opción 5).");
                    } else if (tipo.equals("2")) {
                        System.out.print("Nombre del cuenta habiente: ");
                        String nombre = sc.nextLine().trim();
                        double saldo = leerDouble(sc, "Saldo inicial: ");
                        Cuenta c = new Cuenta(nombre, saldo);
                        cuentas.add(c);
                        actual = cuentas.size() - 1;
                        System.out.println("Cuenta creada con código: " + c.getCodCuenta());
                    } else {
                        System.out.println("Opción inválida. No se creó ninguna cuenta.");
                    }
                    break;
                }

                case "2": { // Cantidad de cuentas creadas (método de clase)
                    System.out.println("Total de cuentas creadas: " + Cuenta.getCantCuentasCreadas());
                    break;
                }

                case "3": { // Listar cuentas
                    if (cuentas.isEmpty()) {
                        System.out.println("No hay cuentas creadas todavía.");
                    } else {
                        System.out.println("\nÍnd. | Código     | Cuenta habiente        | Saldo");
                        System.out.println("-----+------------+------------------------+-----------");
                        for (int i = 0; i < cuentas.size(); i++) {
                            Cuenta c = cuentas.get(i);
                            System.out.printf("  %d  | %-10s | %-22s | %.2f%n",
                                    i,
                                    c.getCodCuenta(),
                                    c.toString().split("\n")[2].split(":")[1].trim(), // nombreCuentaHabiente
                                    c.getSaldo());
                        }
                    }
                    break;
                }

                case "4": { 
                    if (cuentas.isEmpty()) {
                        System.out.println("Debe crear una cuenta primero.");
                        break;
                    }
                    System.out.print("Índice de la cuenta a seleccionar: ");
                    String idxStr = sc.nextLine().trim();
                    try {
                        int idx = Integer.parseInt(idxStr);
                        if (idx >= 0 && idx < cuentas.size()) {
                            actual = idx;
                            System.out.println("Cuenta " + cuentas.get(actual).getCodCuenta() + " seleccionada.");
                        } else {
                            System.out.println("Índice fuera de rango.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Índice inválido.");
                    }
                    break;
                }

                case "5": { 
                    if (!hayCuentaSeleccionada(actual, cuentas)) break;
                    System.out.print("Nuevo nombre del cuenta habiente: ");
                    String nombre = sc.nextLine().trim();
                    cuentas.get(actual).setNombreCuentaHabiente(nombre);
                    System.out.println("Nombre actualizado correctamente.");
                    break;
                }

                case "6": { 
                    if (!hayCuentaSeleccionada(actual, cuentas)) break;
                    double monto = leerDouble(sc, "Monto a depositar: ");
                    double nuevoSaldo = cuentas.get(actual).depositar(monto);
                    System.out.printf("Depósito realizado. Saldo actual: %.2f%n", nuevoSaldo);
                    break;
                }

                case "7": { 
                    if (!hayCuentaSeleccionada(actual, cuentas)) break;
                    double monto = leerDouble(sc, "Monto a retirar: ");
                    double nuevoSaldo = cuentas.get(actual).retirar(monto);
                    System.out.printf("Saldo actual: %.2f%n", nuevoSaldo);
                    break;
                }

                case "8": { 
                    if (!hayCuentaSeleccionada(actual, cuentas)) break;
                    System.out.printf("Saldo disponible: %.2f%n", cuentas.get(actual).getSaldo());
                    break;
                }

                case "9": { 
                    if (!hayCuentaSeleccionada(actual, cuentas)) break;
                    System.out.println(cuentas.get(actual).toString());
                    break;
                }

                case "0": { 
                    salir = true;
                    System.out.println("¡Hasta luego!");
                    break;
                }

                default:
                    System.out.println("Opción inválida. Intente de nuevo.");
            }
        }
        sc.close();
    }

    
    private static boolean hayCuentaSeleccionada(int actual, List<Cuenta> cuentas) {
        if (actual < 0 || cuentas.isEmpty()) {
            System.out.println("Debe crear y seleccionar una cuenta primero.");
            return false;
        }
        return true;
    }

    
    private static double leerDouble(Scanner sc, String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String linea = sc.nextLine().trim();
            try {
                return Double.parseDouble(linea);
            } catch (NumberFormatException e) {
                System.out.println("Valor inválido. Por favor ingrese un número.");
            }
        }
    }
}