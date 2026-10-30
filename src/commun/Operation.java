package commun;
import java.io.Serializable;

public class Operation implements Serializable {
    // Obligatoire pour Serializable (version de la classe)
    private static final long serialVersionUID = 1L;

    private int op1;
    private char operateur;
    private int op2;

    // Constructeur
    public Operation(int op1, char operateur, int op2) {
        this.op1 = op1;
        this.operateur = operateur;
        this.op2 = op2;
    }

    // Getters
    public int getOp1() { return op1; }
    public char getOperateur() { return operateur; }
    public int getOp2() { return op2; }

    @Override
    public String toString() {
        return op1 + " " + operateur + " " + op2;
    }
}