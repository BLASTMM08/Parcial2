import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.ArrayList;
import java.util.List;

/**
 * Representa un usuario con nombre, correo electrónico y contraseña.
 * Esta es una clase record, proporcionando una forma concisa de declarar portadores de datos inmutables.
 * Forma parte del sistema de registro de usuarios que valida la información utilizando expresiones regulares.
 */
record User(String name, String email, String password) {

    /**
     * Proporciona una representación en cadena del Usuario, excluyendo la contraseña por seguridad.
     * @return Una cadena formateada que contiene el nombre y correo electrónico del usuario.
     */
    @Override
    public String toString() {
        return String.format("Nombre: %s | Correo: %s", name, email);
    }
}

/**
 * Proporciona métodos estáticos para validar la entrada del usuario, como nombre, correo electrónico y contraseña,
 * utilizando expresiones regulares predefinidas.
 * Sigue el principio de responsabilidad única al centrarse solo en la validación.
 */
class Validator {
    // Patrón de expresión regular para validar nombres completos.
    // Permite letras mayúsculas y minúsculas (incluyendo vocales acentuadas y Ñ),
    // requiere al menos una palabra que comience con una letra mayúscula y permite espacios entre palabras.
    static final Pattern NAME = Pattern.compile("^[A-ZÁÉÍÓÚÑ][a-záéíóúñ]+( [A-ZÁÉÍÓÚÑ][a-záéíóúñ]+)*$");
    // Patrón de expresión regular para validar direcciones de correo electrónico.
    // Estructura básica: caracteres + @ + caracteres + . + 2-6 letras para el dominio de nivel superior.
    static final Pattern EMAIL = Pattern.compile("^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$");
    // Patrón de expresión regular para validar contraseñas.
    // Requiere al menos dos letras mayúsculas, al menos tres letras minúsculas,
    // al menos un dígito, al menos un carácter especial y una longitud mínima de 8 caracteres.
    static final Pattern PASSWORD = Pattern.compile("^(?=.*[A-Z].*[A-Z])(?=.*[a-z].*[a-z].*[a-z])(?=.*\\d)(?=.*[^a-zA-Z0-9]).{8,}$");

    /**
     * Valida si una cadena dada es un nombre completo válido según el patrón predefinido.
     * @param s La cadena a validar como nombre.
     * @return true si la cadena es un nombre válido, false en caso contrario.
     */
    static boolean validName(String s) { return NAME.matcher(s).matches(); }

    /**
     * Valida si una cadena dada es una dirección de correo electrónico válida según el patrón predefinido.
     * @param s La cadena a validar como correo electrónico.
     * @return true si la cadena es un correo electrónico válido, false en caso contrario.
     */
    static boolean validEmail(String s) { return EMAIL.matcher(s).matches(); }

    /**
     * Valida si una cadena dada es una contraseña válida según el patrón predefinido.
     * @param s La cadena a validar como contraseña.
     * @return true si la cadena es una contraseña válida, false en caso contrario.
     */
    static boolean validPassword(String s) { return PASSWORD.matcher(s).matches(); }
}

/**
 * Gestiona una lista de usuarios registrados.
 * Proporciona funcionalidad para registrar nuevos usuarios después de la validación
 * y listar todos los usuarios registrados.
 * Encapsula la lógica de almacenamiento y gestión de usuarios.
 */
class UserRegistry {
    // Lista para almacenar objetos User registrados.
    final List<User> users = new ArrayList<>();

    /**
     * Registra un nuevo usuario si el nombre, correo electrónico y contraseña proporcionados son válidos.
     * Utiliza la clase Validator para realizar las validaciones.
     * @param name El nombre del usuario.
     * @param email El correo electrónico del usuario.
     * @param password La contraseña del usuario.
     * @return true si el usuario fue registrado exitosamente, false en caso contrario (debido a datos inválidos).
     */
    boolean register(String name, String email, String password) {
        // Valida los datos de entrada utilizando la clase Validator.
        if (!Validator.validName(name) || !Validator.validEmail(email) || !Validator.validPassword(password)) {
            // Si alguna validación falla, retorna false.
            return false;
        }
        // Si todos los datos son válidos, crea un nuevo objeto User y lo añade a la lista.
        users.add(new User(name, email, password));
        return true; // Indica registro exitoso.
    }

    /**
     * Imprime los detalles de todos los usuarios registrados en la consola.
     * Si no hay usuarios registrados, imprime un mensaje indicándolo.
     */
    void listUsers() {
        // Verifica si la lista de usuarios está vacía.
        if (users.isEmpty()) {
            System.out.println("No hay usuarios registrados.");
            return; // Sale del método si no hay usuarios registrados.
        }
        // Itera a través de la lista de usuarios e imprime los detalles de cada usuario
        // utilizando el método toString sobrescrito en la clase User.
        users.forEach(System.out::println);
    }
}

/**
 * Clase principal de la aplicación para el sistema de Registro de Usuarios.
 * Proporciona una interfaz de consola para que los usuarios se registren y vean los usuarios registrados.
 * Demuestra la aplicación de la Programación Orientada a Objetos (POO)
 * al utilizar clases separadas para la validación y el registro de usuarios.
 */
public class RegistroUsuariosApp {
    /**
     * Método principal para ejecutar la aplicación de registro de usuarios.
     * Inicializa el Scanner y el UserRegistry, luego entra en un bucle
     * para solicitar al usuario los detalles de registro hasta que se ingresa 'exit' para el nombre.
     * Finalmente, lista todos los usuarios registrados.
     * @param args Argumentos de línea de comandos (no utilizados).
     */
    public static void main(String[] args) {
        // Inicializa un objeto Scanner para leer la entrada del usuario desde la consola.
        Scanner scanner = new Scanner(System.in);
        // Inicializa el UserRegistry para gestionar el registro de usuarios.
        UserRegistry registry = new UserRegistry();

        System.out.println("\n=== Registro de Usuarios ===\nEscribe 'exit' como nombre para salir.\n");

        // Bucle principal de la aplicación: continúa hasta que el usuario ingresa 'exit' para el nombre.
        while (true) {
            // Solicita y lee el nombre completo del usuario.
            System.out.print("Nombre completo: ");
            String name = scanner.nextLine();
            // Verifica si el usuario quiere salir.
            if (name.equalsIgnoreCase("exit")) break;

            // Solicita y lee la dirección de correo electrónico del usuario.
            System.out.print("Correo electrónico: ");
            String email = scanner.nextLine();

            // Solicita y lee la contraseña del usuario.
            System.out.print("Contraseña: ");
            String password = scanner.nextLine();

            // Intenta registrar al usuario con los detalles proporcionados.
            if (registry.register(name, email, password)) {
                // Si el registro es exitoso, imprime un mensaje de éxito.
                System.out.println("✔ Registro exitoso\n");
            } else {
                // Si el registro falla (debido a datos inválidos), imprime un mensaje de error.
                System.out.println("✖ Datos inválidos. Inténtalo de nuevo.\n");
            }
        }

        // Después de que el bucle principal finaliza (el usuario sale), imprime la lista de todos los usuarios registrados.
        System.out.println("\n=== Lista de usuarios registrados ===");
        registry.listUsers();
        // Cierra el recurso Scanner para prevenir fugas de recursos.
        scanner.close();
    }
}