package AST;

import java.util.Iterator;

public abstract class AST_LIST extends AST_Node implements Iterable<AST_Node>{

    public abstract AST_Node getHead();
    public abstract AST_LIST getTail();

    @Override
    public Iterator<AST_Node> iterator() {
        return new ASTListIterator(this);
    }
}
