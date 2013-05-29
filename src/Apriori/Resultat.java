package Apriori;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Vector;

public class Resultat implements Serializable {
	/**
	 * ID servant à la vérification lors de la désérialisation
	 */
	private static final long serialVersionUID = -1071825430991686699L;
	private Vector<Doublet> results;

	public class Doublet implements Serializable
	{
		/**
		 * ID servant à la vérification lors de la désérialisation
		 */
		private static final long serialVersionUID = -1555911656755045508L;
		private String[] attributs;
		private float indice;
		
		public String[] getAttributs() {
			return attributs;
		}
		public void setAttributs(String[] attributs) {
			this.attributs = attributs;
		}
		public float getIndice() {
			return indice;
		}
		public void setIndice(float indice) {
			this.indice = indice;
		}
		public Doublet(String[] attributs, float indice) {
			super();
			this.attributs = attributs;
			this.indice = indice;
		}
		@Override
		public String toString() {
			return "Doublet [attributs=" + Arrays.toString(attributs)
					+ ", indice=" + indice + "]";
		}
	}
	
	public Resultat() {
		super();
		results = new Vector<>(); // Attention JDK 7
	}
	
	public void addResults(String argument, int confidence)
	{
		results.add(new Doublet(argument.split("\\s+"), confidence));
	}

	@Override
	public String toString() {
		return "Resultat [results=" + results + "]";
	}
}
