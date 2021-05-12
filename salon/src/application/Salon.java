package application;

public class Salon 
{
	private String prenom;
	private String nom;
	private Double age;
	private String procedure;
	private Double cout;
	
	//constructeur vide
	public Salon()
	{
		this(null,null);
	}
	
	//constructeur avec 2 parametres
	public Salon(String prenom, String nom)
	{
		this.prenom=prenom;
		this.nom=nom;
		this.procedure="";
		this.age=0.0;
		this.cout=0.0;
	}
	
	// Getters et Setters 
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public Double getAge() {
		return age;
	}
	public void setAge(Double age) {
		this.age = age;
	}
	public String getProcedure() {
		return procedure;
	}
	public void setProcedure(String procedure) {
		this.procedure = procedure;
	}
	
	public Double getCout() {
		return cout;
	}
	public void setCout(Double cout) {
		this.cout = cout;
	}
	
	
	
}
