
/**
 * Beschreiben Sie hier die Klasse Entry.
 * 
 * @author M. Hoffmann 
 * @version 13.01.2018
 */
public class Entry implements ComparableContent<Entry> {
  int wert;
  
  public Entry(int pContent){
      wert = pContent;
    }
  
  public boolean isLess(Entry pContent) { 
      return this.getWert() < pContent.getWert(); 
  }
  
  public boolean isEqual(Entry pContent) { 
      return this.getWert() == pContent.getWert(); 
  }   
  
  public boolean isGreater(Entry pContent) { 
      return this.getWert() > pContent.getWert(); 
  }
  
  public int getWert() { 
    return this.wert; 
  } 
}
