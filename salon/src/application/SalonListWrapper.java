package application;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="clients")
public class SalonListWrapper
{
	private List<Salon> clients;
	@XmlElement(name= "clients")
	public List<Salon> getClients()
	{
		return clients;
	}

	public void setClients(List<Salon> clients)
	{
		this.clients=clients;
	}
}