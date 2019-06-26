package it.polito.tdp.ufo;

public class Anno implements Comparable<Anno>{
	
	private int anno;
	private int avvistamenti;

	public Anno(int anno, int avvistamenti) {
		super();
		this.anno = anno;
		this.avvistamenti = avvistamenti;
	}
	
	public int getAnno() {
		return anno;
	}

	public int getAvvistamenti() {
		return avvistamenti;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + anno;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Anno other = (Anno) obj;
		if (anno != other.anno)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return anno+" - "+avvistamenti;
	}

	@Override
	public int compareTo(Anno a) {
		return this.anno - a.anno;
	}
	
}