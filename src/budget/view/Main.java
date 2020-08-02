package budget.view;

import static budget.view.Main.sc;
import budget.controller.ControllerUI;
import java.util.Scanner;

/**
 * @author Luis
 */
public class Main {

    protected static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        int elec = 0;
        ControllerUI ui = new ControllerUI();
        while (true) {
            ui.text();
            try {
                elec = Integer.parseInt(sc.nextLine());
                System.out.println("");
                ui.menu(elec);
            } catch (NumberFormatException ex) {
            }
        }
    }
}
