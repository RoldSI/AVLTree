/**
 * <p>
 * Materialien zu den zentralen NRW-Abiturpruefungen im Fach Informatik ab 2018
 * </p>
 * <p>
 * Generische Klasse AVLTree<ContentType>
 * </p>
 * <p>
 * Mithilfe der generischen Klasse AVLTree koennen beliebig viele
 * Objekte in einem Binaerbaum (binaerer Suchbaum) entsprechend einer
 * Ordnungsrelation verwaltet werden. <br />
 * Ein Objekt der Klasse stellt entweder einen leeren binaeren Suchbaum dar oder
 * verwaltet ein Inhaltsobjekt sowie einen linken und einen rechten Teilbaum,
 * die ebenfalls Objekte der Klasse AVLTree sind.<br />
 * Die Klasse der Objekte, die in dem Suchbaum verwaltet werden sollen, muss
 * das generische Interface ComparableContent implementieren. Dabei muss durch
 * Ueberschreiben der drei Vergleichsmethoden isLess, isEqual, isGreater (s.
 * Dokumentation des Interfaces) eine eindeutige Ordnungsrelation festgelegt
 * sein. <br />
 * Alle Objekte im linken Teilbaum sind kleiner als das Inhaltsobjekt des
 * binaeren Suchbaums. Alle Objekte im rechten Teilbaum sind groesser als das
 * Inhaltsobjekt des binaeren Suchbaums. Diese Bedingung gilt (rekursiv) auch in
 * beiden Teilbaeumen. <br />
 * Hinweis: In dieser Version wird die Klasse BinaryTree nicht benutzt.
 * </p>
 * 
 * @author Qualitaets- und UnterstuetzungsAgentur - Landesinstitut fuer Schule
 * @version Generisch_03 2017-11-28
 */
public class AVLTree<ContentType extends ComparableContent<ContentType>> {

    /* --------- Anfang der privaten inneren Klasse -------------- */
    
    private ContentType content;
    private AVLTree<ContentType> left, right, parent;

    /**
     * Der Konstruktor erzeugt einen leeren Suchbaum.
     */
    public AVLTree(AVLTree<ContentType> pParent) {
        this.content = null;
        this.left = null;
        this.right = null;
        this.parent = pParent;
    }

    /**
     * Diese Anfrage liefert den Wahrheitswert true, wenn der Suchbaum leer ist,
     * sonst liefert sie den Wert false.
     * 
     * @return true, wenn der binaere Suchbaum leer ist, sonst false
     * 
     */
    public boolean isEmpty() {
        return this.content == null;
    }

    /**
     * Falls der Parameter null ist, geschieht nichts.<br />
     * Falls ein bezueglich der verwendeten Vergleichsmethode isEqual mit
     * pContent uebereinstimmendes Objekt im geordneten binaeren Suchbau
     * enthalten ist, passiert nichts. <br />
     * Achtung: hier wird davon ausgegangen, dass isEqual genau dann true
     * liefert, wenn isLess und isGreater false liefern. <br />
     * Andernfalls (isLess oder isGreater) wird das Objekt pContent entsprechend
     * der vorgegebenen Ordnungsrelation in den AVLTree eingeordnet.
     * 
     * @param pContent
     *            einzufuegendes Objekt vom Typ ContentType
     *            
     */
    public void insert(ContentType pContent) {
        if (pContent != null) {
            if (isEmpty()) {
                this.content = pContent;
                this.left = new AVLTree<ContentType>(this);
                this.right = new AVLTree<ContentType>(this);
                System.out.println("insert: " + this.content.toString());
                if (this.parent != null) this.parent.rotateOnInsert();
            } else if (pContent.isLess(this.content)) {
                if (this.getLeftTree() == null) {
                    this.left = new AVLTree<ContentType>(this);
                }
                this.left.insert(pContent);
            } else if(pContent.isGreater(this.content)) {
                if (this.getRightTree() == null) {
                    this.right = new AVLTree<ContentType>(this);
                }
                this.right.insert(pContent);
            }
        }
    }

    /**
     * Diese Anfrage liefert den linken Teilbaum des binaeren Suchbaumes. <br />
     * Wenn er leer ist, wird null zurueckgegeben.
     * 
     * @return den linken Teilbaum (Objekt vom Typ AVLTree<ContentType>) 
     *         bzw. null, wenn der Suchbaum leer ist
     *         
     */
    public AVLTree<ContentType> getLeftTree() {
        if (this.isEmpty()) {
            return null;
        } else {
            return this.left;
        }
    }

    /**
     * Diese Anfrage liefert das Inhaltsobjekt des Suchbaumes. Wenn der Suchbaum
     * leer ist, wird null zurueckgegeben.
     * 
     * @return das Inhaltsobjekt vom Typ ContentType bzw. null, wenn der aktuelle
     *         Suchbaum leer ist
     *         
     */
    public ContentType getContent() {
        if (this.isEmpty()) {
            return null;
        } else {
            return this.content;
        }
    }

    /**
     * Diese Anfrage liefert den rechten Teilbaum des binaeren Suchbaumes. <br />
     * Wenn er leer ist, wird null zurueckgegeben.
     * 
     * @return den rechten Teilbaum (Objekt vom Typ AVLTree<ContentType>) 
     *         bzw. null, wenn der aktuelle Suchbaum leer ist
     *         
     */
    public AVLTree<ContentType> getRightTree() {
        if (this.isEmpty()) {
            return null;
        } else {
            return this.right;
        }
    }

    /**
     * Falls ein bezueglich der verwendeten Vergleichsmethode mit
     * pContent uebereinstimmendes Objekt im binaeren Suchbaum enthalten
     * ist, wird dieses entfernt. Falls der Parameter null ist, aendert sich
     * nichts.
     * 
     * @param pContent
     *            zu entfernendes Objekt vom Typ ContentType
     *            
     */
    public void remove(ContentType pContent) {
        if (isEmpty() || pContent == null ) {
            // Abbrechen, da kein Element zum entfernen vorhanden ist.
          return;
        }
        
        if (pContent.isLess(this.content)) {
            // Element ist im linken Teilbaum zu loeschen.
            this.left.remove(pContent);
        } else if (pContent.isGreater(this.content)) {
            // Element ist im rechten Teilbaum zu loeschen.
            this.right.remove(pContent);
        } else {
            // Element ist gefunden.
            if (this.left.isEmpty()) {
                if (this.right.isEmpty()) {
                    // Es gibt keinen Nachfolger.
                    this.content = null;
                    this.right = null;
                    this.left = null;
                    //if(this.parent != null) this.parent.rotateOnRemove();
                } else {
                    // Es gibt nur rechts einen Nachfolger.
                    //node = getNodeOfRightSuccessor();
                    this.content = this.getRightTree().getContent();
                    this.left = this.getRightTree().getLeftTree();
                    if (this.left == null) System.out.println("DAS IST VERFLUCHTE SCHEISSE MATTHIAS");
                    this.left.parent = this;
                    this.right = this.getRightTree().getRightTree();
                    if (this.right == null) System.out.println("DAS IST VERFLUCHTE SCHEISSE ALTER");
                    this.right.parent = this;
                    //if(this.parent != null) this.parent.rotateOnRemove();
                }
            } else if (this.right.isEmpty()) {
                // Es gibt nur links einen Nachfolger.
                //node = getNodeOfLeftSuccessor();
                this.content = this.getLeftTree().getContent();
                this.right = this.getLeftTree().getRightTree();
                this.right.parent = this;
                this.left = this.getLeftTree().getLeftTree();
                this.left.parent = this;
                //if(this.parent != null) this.parent.rotateOnRemove();
            } else {
                // Es gibt links und rechts einen Nachfolger.
                if (this.getRightTree().left.isEmpty()) {
                    // Der rechte Nachfolger hat keinen linken Nachfolger.
                    this.content = getRightTree().content;
                    this.right = getRightTree().right;
                    this.right.parent = this;
                    //if(this.parent != null) this.parent.rotateOnRemove();
                } else { //HIER
                    AVLTree<ContentType> previous = this.right
                            .ancestorOfSmallRight();
                    AVLTree<ContentType> smallest = previous.left;
                    this.content = smallest.content;
                    previous.remove(smallest.content);

                }
            }
        }       
    }

    /**
     * Falls ein bezueglich der verwendeten Vergleichsmethode isEqual mit
     * pContent uebereinstimmendes Objekt im binaeren Suchbaum enthalten ist,
     * liefert die Anfrage dieses, ansonsten wird null zurueckgegeben. <br />
     * Falls der Parameter null ist, wird null zurueckgegeben.
     * 
     * @param pContent
     *            zu suchendes Objekt vom Typ ContentType
     * @return das gefundene Objekt vom Typ ContentType, bei erfolgloser Suche null
     * 
     */
    public ContentType search(ContentType pContent) {
        if (this.isEmpty() || pContent == null) {
            // Abbrechen, da es kein Element zu suchen gibt.
            return null;
        } else {
            //ContentType content = this.getContent();
            if (pContent.isLess(this.content)) {
                // Element wird im linken Teilbaum gesucht.
                return this.getLeftTree().search(pContent);
            } else if (pContent.isGreater(this.content)) {
                // Element wird im rechten Teilbaum ge  sucht.
                return this.getRightTree().search(pContent);
            } else if (pContent.isEqual(this.content)) {
                // Element wurde gefunden.
              return this.content;               
            } else {    
              // Dieser Fall sollte nicht auftreten.
                return null;
            }
        }
    }

    public AVLTree<ContentType> find(ContentType pContent) {
        if (this.isEmpty() || pContent == null) {
            // Abbrechen, da es kein Element zu suchen gibt.
            return null;
        } else {
            //ContentType content = this.getContent();
            if (pContent.isLess(this.content)) {
                // Element wird im linken Teilbaum gesucht.
                return this.getLeftTree().find(pContent);
            } else if (pContent.isGreater(this.content)) {
                // Element wird im rechten Teilbaum ge  sucht.
                return this.getRightTree().find(pContent);
            } else if (pContent.isEqual(this.content)) {
                // Element wurde gefunden.
              return this;               
            } else {    
              // Dieser Fall sollte nicht auftreten.
                return null;
            }
        }
    }

    public void rotateOnInsert() {
        
        switch (this.balanceFactor()) {
            case 1:
                System.out.println("case 1");
                if(this.parent != null) this.parent.rotateOnInsert();
                break;
            case -1:
                System.out.println("case -1");
                if(this.parent != null) this.parent.rotateOnInsert();
                break;
            case 2:
                System.out.println("case 2");
                if(this.parent != null) this.rotate();
                return;
            case -2:
                System.out.println("case -2");
                if(this.parent != null) this.rotate();
                return;
            default:
                System.out.println("case 0");
                return;
        }

    }

    public void rotateOnRemove() {
        
        switch (this.balanceFactor()) {
            case 0:
                System.out.println("case 0");
                if(this.parent != null) this.parent.rotateOnInsert();
                break;
            case 2:
                System.out.println("case 2");
                if(this.parent != null) this.rotate(); //rebalancing needed here
                this.parent.rotateOnRemove();
                break;
            case -2:
                System.out.println("case -2");
                if(this.parent != null) this.rotate(); //rebalancing needed here
                this.parent.rotateOnRemove();
                break;
            default:
                System.out.println("case -1 & 1");
                return;
        }

    }

    public void rotate() {

        if (this.right.height() > this.left.height()) {
            if (this.right.left.height() > this.right.right.height()) {
                System.out.println("right left");
                this.rotateRightLeft();
            } else {
                System.out.println("right right");
                this.rotateRightRight();
            }
        } else if (this.right.height() < this.left.height()) {
            if (this.left.right.height() > this.left.left.height()) {
                System.out.println("left right");
                this.rotateLeftRight();
            } else {
                System.out.println("left left");
                this.rotateLeftLeft();
            }
        }

    }

    public void rotateRightRight() {

        AVLTree<ContentType> X = this;
        AVLTree<ContentType> Z = this.getRightTree();
        AVLTree<ContentType> t23 = Z.getLeftTree();
        AVLTree<ContentType> par = this.parent;

        if(par.getLeftTree() == X) {
            par.left = Z;
        } else if (par.getRightTree() == X) {
            par.right = Z;
        }
        Z.parent = par;

        Z.left = X;
        X.parent = Z;

        X.right = t23;
        t23.parent = X;

    }

    public void rotateLeftLeft() {

        AVLTree<ContentType> X = this;
        AVLTree<ContentType> Z = this.getLeftTree();
        AVLTree<ContentType> t23 = Z.getRightTree();
        AVLTree<ContentType> par = this.parent;

        if(par.getLeftTree() == X) {
            par.left = Z;
        } else if (par.getRightTree() == X) {
            par.right = Z;
        }
        Z.parent = par;

        Z.right = X;
        X.parent = Z;

        X.left = t23;
        t23.parent = X;

    }
    
    public void rotateRightLeft() {

        AVLTree<ContentType> X = this;
        AVLTree<ContentType> Z = this.getRightTree();
        AVLTree<ContentType> Y = Z.getLeftTree();
        AVLTree<ContentType> t2 = Y.getLeftTree();
        AVLTree<ContentType> t3 = Y.getRightTree();
        AVLTree<ContentType> par = this.parent;

        
        if(par.getLeftTree() == X) {
            par.left = Y;
        } else if (par.getRightTree() == X) {
            par.right = Y;
        }
        Y.parent = par;

        Y.left = X;
        X.parent = Y;

        Y.right = Z;
        Z.parent = Y;

        Z.left = t3;
        t3.parent = Z;

        X.right = t2;
        t2.parent = X;

    }

    public void rotateLeftRight() {

        AVLTree<ContentType> X = this;
        AVLTree<ContentType> Z = this.getLeftTree();
        AVLTree<ContentType> Y = Z.getRightTree();
        AVLTree<ContentType> t2 = Y.getLeftTree();
        AVLTree<ContentType> t3 = Y.getRightTree();
        AVLTree<ContentType> par = this.parent;

        
        if(par.getLeftTree() == X) {
            par.left = Y;
        } else if (par.getRightTree() == X) {
            par.right = Y;
        }
        Y.parent = par;

        Y.left = Z;
        Z.parent = Y;

        Y.right = X;
        X.parent = Y;

        Z.right = t2;
        t2.parent = Z;

        X.left = t3;
        t3.parent = X;

    }
    
    public int height() {
        if (this.isEmpty()) return 0;
        int heightLeft = 0;
        if(this.left != null) heightLeft = this.left.height();
        int heightRight = 0;
        if(this.right != null) heightRight = this.right.height();
        return Math.max(heightLeft, heightRight)+1;
    }
    
    public int balanceFactor() {
        if (this.isEmpty()) return 0;
        return this.right.height()-this.left.height();
    }

    /* ----------- Weitere private Methoden -------------- */

    /**
     * Die Methode liefert denjenigen Baum, dessen linker Nachfolger keinen linken
     * Nachfolger mehr hat. Es ist also spaeter moeglich, in einem Baum im
     * rechten Nachfolger den Vorgaenger des linkesten Nachfolgers zu finden.
     * 
     */
    private AVLTree<ContentType> ancestorOfSmallRight() {      
        if (getLeftTree().left.isEmpty()) {
            return this;
        } else {
            return this.left.ancestorOfSmallRight();
        }
    }

    /*private BSTNode<ContentType> getNodeOfLeftSuccessor() {
        return node.left.node;
    }

    private BSTNode<ContentType> getNodeOfRightSuccessor() {
        return node.right.node;
    }*/
    
}
