package estrategia;

import interfaces.EstrategiaLiteral;

public class LiteralPorRango implements EstrategiaLiteral {
    @Override
    public String calcularLiteral(double promedio) {
        if (promedio > 90 && promedio <= 100) return "A";
        if (promedio > 80) return "B";
        if (promedio > 70) return "C";
        return "D";
    }
}