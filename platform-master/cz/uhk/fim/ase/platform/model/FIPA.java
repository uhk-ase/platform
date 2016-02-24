package cz.uhk.fim.ase.platform.model;

public class FIPA {
	public int FIPA(Message zprava) {
		switch (zprava.getFipa_type()) {
		case "Accept_Proposal":
			//Pøijímá pøedložený návrh k provedení akce.
			break;
			
		case "Agree":
			//Souhlasí s provedením nìjaké akce.
			break;
			
		case "Cancel":
			//Informuje o tom, že již nehodlá provádìt døíve požadovanou akci.
			break;
			
		case "Call_for_Proposal":
			//Vyzývá k pøedložení návrhù na provedení dané akce.
			break;
			
		case "Failure":
			//Informuje agenta o tom, že došlo k pokusu o provedení akce, avšak pokus byl neúspìšný.
			break;
			
		case "Inform":
			//Zasílá pozitivní informativní zprávu.
			break;
		
		case "Refuse":
			//Zamítá provedení dané akce a podává vysvìtlení.
			break;
			
		case "Reject_Proposal":
			//Zamítá návrh na provedení nìjaké akce bìhem vyjednávání.
			break;
			
		case "Request":
			//Odesílá požadavek na provedení dané akce.
			break;
			
		default:
			break;
		}
		
		return (Integer) null;
	}
	
}
