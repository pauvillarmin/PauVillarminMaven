package com.xavierpauvillarmin.tema4maven;

import com.github.lalyos.jfiglet.FigletFont;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {

        // Ejercicio 4: Banner ASCII
        String texto = "Xavier Pau Villarmin Sapena";
        String banner = FigletFont.convertOneLine(texto);

        // Ejercicio 5: Lista de líneas
        List<String> lines = new ArrayList<>();

        // Añadir banner línea a línea
        String[] bannerLines = banner.split("\n");
        for (String line : bannerLines) {
            lines.add(line);
        }

        // Líneas de CV
        lines.add("Nombre y apellidos: Xavier Pau Villarmin Sapena");
        lines.add("Fecha nacimiento: 18/12/1981");
        lines.add("Estudios: ASI, DAW, Maestro Educación Física");
        lines.add("Curso actual: 1º DAW");
        lines.add("Centro: Instituto María Enriquez");
        lines.add("Año: 2026");
        lines.add("Lenguajes: Java, HTML, CSS");
        lines.add("Herramientas: IntelliJ IDEA, Eclipse");
        lines.add("Control de versiones: Git / GitHub");
        lines.add("Intereses: Desarrollo de software");
        lines.add("Idiomas: castellano (nativo), inglés (B2), valenciano (C2)");

        // Ejercicio 6: Lanterna (crear Screen)
        Screen screen = new DefaultTerminalFactory().createScreen();
        screen.startScreen();
        screen.setCursorPosition(null);

        try {
            // EJERCICIO 8: Animación (scroll hacia arriba)

            int height = screen.getTerminalSize().getRows();

            // Empieza "debajo" para que el contenido vaya entrando desde abajo
            int yOffset = height;

            // Recorremos
            // Cuando la última línea ya está por encima (y < 0), terminamos
            while (yOffset > -lines.size()) {

                // 1) Dibujar
                drawFrame(screen, lines, yOffset);

                // 2) Esperar 100 ms
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ignored) {
                }

                // 3) Mover hacia arriba
                yOffset--;
            }

        } finally {
            screen.stopScreen();
        }
    }

    // Ejercicio 7: Dibujar frame
    private static void drawFrame(Screen screen, List<String> lines, int yOffset) throws IOException {
        TerminalSize size = screen.getTerminalSize();
        int width = size.getColumns();
        int height = size.getRows();

        screen.clear();
        TextGraphics tg = screen.newTextGraphics();

        for (int i = 0; i < lines.size(); i++) {
            int y = yOffset + i;
            if (y < 0 || y >= height) continue;

            String line = lines.get(i);

            // Centrado horizontal
            int x = Math.max(0, (width - line.length()) / 2);
            if (x >= width) continue;

            // Recorte simple si se sale por la derecha
            String visible = (line.length() > width) ? line.substring(0, width) : line;

            tg.putString(x, y, visible);
        }

        screen.refresh();
    }
}
