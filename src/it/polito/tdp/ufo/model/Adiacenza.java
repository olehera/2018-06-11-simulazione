package it.polito.tdp.ufo.model;

public class Adiacenza {
	
	private String primo;
	private String secondo;
	
	public Adiacenza(String primo, String secondo) {
		super();
		this.primo = primo;
		this.secondo = secondo;
	}

	public String getPrimo() {
		return primo;
	}

	public String getSecondo() {
		return secondo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((primo == null) ? 0 : primo.hashCode());
		result = prime * result + ((secondo == null) ? 0 : secondo.hashCode());
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
		Adiacenza other = (Adiacenza) obj;
		if (primo == null) {
			if (other.primo != null)
				return false;
		} else if (!primo.equals(other.primo))
			return false;
		if (secondo == null) {
			if (other.secondo != null)
				return false;
		} else if (!secondo.equals(other.secondo))
			return false;
		return true;
	}

}