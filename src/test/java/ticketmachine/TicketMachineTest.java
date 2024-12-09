package ticketmachine;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

class TicketMachineTest {
	private static final int PRICE = 50; // Une constante

	private TicketMachine machine; // l'objet à tester

	@BeforeEach
	public void setUp() {
		machine = new TicketMachine(PRICE); // On initialise l'objet à tester
	}

	@Test
	// On vérifie que le prix affiché correspond au paramètre passé lors de
	// l'initialisation
	// S1 : le prix affiché correspond à l’initialisation.
	void priceIsCorrectlyInitialized() {
		// Paramètres : valeur attendue, valeur effective, message si erreur
		assertEquals(PRICE, machine.getPrice(), "Initialisation incorrecte du prix");
	}

	@Test
	// S2 : la balance change quand on insère de l’argent
	void insertMoneyChangesBalance() {
		// GIVEN : une machine vierge (initialisée dans @BeforeEach)
		// WHEN On insère de l'argent
		machine.insertMoney(10);
		machine.insertMoney(20);
		// THEN La balance est mise à jour, les montants sont correctement additionnés
		assertEquals(10 + 20, machine.getBalance(), "La balance n'est pas correctement mise à jour");
	}


	//S3 : on n’imprime pas leticket si le montant inséré est insuffisant
	@Test
	void nImprimePasSiPasAssezDArgent(){
		// GIVEN : une machine avec un prix de 50
		// WHEN : on insère 30
		machine.insertMoney(PRICE -1);
		// THEN : on ne peut pas imprimer le ticket
		assertFalse(machine.printTicket(), "On a pu imprimer le ticket alors qu'on avait pas insérer assez d'argent ");
	}



	//S4 : on imprime le ticket si le montant inséré est suffisant
	@Test
	void ImprimeTicketSiAssezDArgent(){
		machine.insertMoney(PRICE +1);
		assertTrue(machine.printTicket(), "On n'a pas pu imprimer de ticket alors qu'il y avait assez d'argent");
	}

	//S5 : Quand on imprime un ticket la balance est décrémentée du prix du ticket
	@Test
	void BalanceDecrementee(){
		machine.insertMoney(PRICE);
		int balance = machine.getBalance();
		machine.printTicket();
		assertEquals(balance - PRICE,machine.getBalance(), "La balance n'est pas a jour");
	}

	//S6 : le montant collecté est mis à jour quand on imprime un ticket (pas avant)

	@Test
	void PrixQueRecupereLaMachine(){
		machine.insertMoney(PRICE + 30);
		int total = machine.getTotal();
		machine.printTicket();
		assertEquals(total + PRICE,machine.getTotal(), "Le total n'est pas a jour");
	}

	//S7 : refund() rend correctement la monnaie
	@Test
	void RendLaMonaie(){
		machine.insertMoney(PRICE + 30);
		assertEquals(PRICE + 30,machine.refund(), "La somme entré n'a pas été rendu");
	}

	//S8 : refund()remet la balance à zéro
	@Test
	void BalanceAZero(){
		machine.insertMoney(PRICE + 30);
		machine.refund();
		assertEquals(0,machine.getBalance(), "La somme entré n'a pas été rendu");
	}

	//S9 : on ne peut pas insérer un montant négatif
	@Test
	void MontantNegatif(){
		try{
			machine.insertMoney(-10);
			fail("Cet appel doit lever une exception");
		}
		catch(IllegalArgumentException e){

		}
	}

	//S10 : on ne peut pas créer de machine qui délivre des tickets dont le prix est négatif
	@Test
	void MachineTicketNegatif(){

		try{
			TicketMachine machine2 = new TicketMachine(-10);
			fail("Cet appel doit lever une exception");
		}
		catch(IllegalArgumentException e){

		}
	}

}
