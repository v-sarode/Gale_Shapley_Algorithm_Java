import java.util.Scanner;
class ProposeReject{
	public static void main(String[] args){ 
		 System.out.println("\t\tPROPOSE REJECT ALGORITHM\t");
		 System.out.println("\t\t------------------------\t\n");
		 String[] men =   {"Victor", "Wyatt", "Xavier", "Yancey", "Zeus"};   
	     String[] women = {"Amy","Bertha","Clare","Diane","Erika"};
	     String[][] menPreference = {		{"Bertha","Amy","Diane","Erika","Clare"},
		        							{"Diane","Bertha","Amy","Clare","Erika"},
		        							{"Bertha","Erika","Clare","Diane","Amy"},
		        							{"Amy","Diane","Clare","Bertha","Erika",},
		        							{"Bertha","Diane","Amy","Erika","Clare"},
	        						 };
	                              
	    String[][] womenPreference = {		{"Zeus","Victor","Wyatt","Yancey","Xavier"},
											{"Xavier","Wyatt","Yancey","Victor","Zeus"},
											{"Wyatt","Xavier","Yancey","Zeus","Victor",},
											{"Victor","Zeus","Yancey","Xavier","Wyatt"},
											{"Yancey","Wyatt","Zeus","Xavier","Victor",},
	    							  };  

		System.out.println("\tMAKE CHOICE TO SELECT HOW TO MAKE PAIRS \n ");
		System.out.println("\t1. MEN TO WOMEN IN NORMAL ORDER ");
		System.out.println("\t2. MEN TO WOMEN IN ( MEN IN ) REVERSE ORDER ");
		System.out.println("\t3. WOMEN TO MEN IN NORMAL ORDER ");
		System.out.print("\n\tENTER YOUR CHOICE :  ");
		Scanner userChoice = new Scanner(System.in);
		int userInput = userChoice.nextInt();
		Propose startProposing = new Propose(men, women,womenPreference,menPreference,userInput);
	}
}