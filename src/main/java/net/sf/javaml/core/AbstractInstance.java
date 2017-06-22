/**
 * %SVN.HEADER%
 */
package net.sf.javaml.core;

import java.util.Iterator;

/**
 * Implementation of some standard methods for instances.
 * 
 * 
 * 
 * @see Instance
 * 
 * @version %SVN.REVISION%
 * 
 * @author Thomas Abeel
 * 
 */
public abstract class AbstractInstance implements Instance {
    /**
	 * 
	 */
	private static final long serialVersionUID = -1712202124913999825L;

	public static int nextID = 0;

    private final int ID;

    public int getID() {
        return ID;
    }

    class InstanceValueIterator implements Iterator<Double> {

        private int index = 0;

        
        public boolean hasNext() {
            return index < noAttributes();
        }

        
        public Double next() {
            index++;
            return value(index - 1);
        }

        
        public void remove() {
            throw new UnsupportedOperationException("Cannot remove from instance using the iterator.");

        }

    }

    
    public Iterator<Double> iterator() {
        return new InstanceValueIterator();
    }

    private Object classValue;

    protected AbstractInstance() {
        this(null);
    }

    protected AbstractInstance(Object classValue) {
        ID = nextID;
        nextID++;
        this.classValue = classValue;
    }

    
    public Object classValue() {
        return classValue;
    }

    
    public void setClassValue(Object classValue) {
        this.classValue = classValue;
    }

    
    public Instance minus(Instance min) {
        Instance out = new DenseInstance(new double[this.noAttributes()]);
        for (int i = 0; i < this.noAttributes(); i++) {
            out.put(i, this.get(i) - min.get(i));
        }
        return out;

    }

    
    public Instance minus(double min) {
        Instance out = new DenseInstance(new double[this.noAttributes()]);
        for (int i = 0; i < this.noAttributes(); i++) {
            out.put(i, this.get(i) - min);
        }
        return out;

    }

    
    public Instance divide(double min) {
        Instance out = new DenseInstance(new double[this.noAttributes()]);
        for (int i = 0; i < this.noAttributes(); i++) {
            out.put(i, this.get(i) / min);
        }
        return out;
    }

    
    public Instance multiply(double value) {
        Instance out = new DenseInstance(new double[this.noAttributes()]);
        for (int i = 0; i < this.noAttributes(); i++) {
            out.put(i, this.get(i) * value);
        }
        return out;
    }

    
    public int hashCode() {
        return ID;
    }

    
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final AbstractInstance other = (AbstractInstance) obj;
        if (ID != other.ID)
            return false;
        return true;
    }

    
    public Instance multiply(Instance value) {
        Instance out = new DenseInstance(new double[this.noAttributes()]);
        for (int i = 0; i < this.noAttributes(); i++) {
            out.put(i, this.get(i) * value.get(i));
        }
        return out;
    }

    
    public Instance divide(Instance min) {
        Instance out = new DenseInstance(new double[this.noAttributes()]);
        for (int i = 0; i < this.noAttributes(); i++) {
            out.put(i, this.get(i) / min.get(i));
        }
        return out;
    }

    
    public Instance add(double min) {
        Instance out = new DenseInstance(new double[this.noAttributes()]);
        for (int i = 0; i < this.noAttributes(); i++) {
            out.put(i, this.get(i) + min);
        }
        return out;
    }

    
    public Instance add(Instance min) {
        Instance out = new DenseInstance(new double[this.noAttributes()]);
        for (int i = 0; i < this.noAttributes(); i++) {
            out.put(i, this.get(i) + min.get(i));
        }
        return out;
    }

    
    public Instance sqrt() {
        Instance out = new DenseInstance(new double[this.noAttributes()]);
        for (int i = 0; i < this.noAttributes(); i++) {
            out.put(i, Math.sqrt(this.get(i)));
        }
        return out;
    }
}
