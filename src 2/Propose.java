class Propose{
	
	int nMen,nWomen;
	int[] womenPartnerIndex,menPartnerIndex,womenToMenPartnerRank,menToWomenPartnerRank,mansProposeCount,womensProposeCount;
	String[] men,women,menPartnerName,womenPartnerName;
	String[][] menPreference,womenPreferece;
	boolean[] isManEngaged,isWomenEngaged;
	
	int programMode = 0, menEngagedCount = 0,womenEngagedCount = 0;
	
	public Propose(String[] men, String[] women, String[][] womenPreference, String[][] menPreference, int usersChoice){
		
		programMode = usersChoice;
		nMen = men.length;
		nWomen = women.length;
		this.men = new String[nMen];
		this.women = new String[nWomen];
		menPartnerName = new String[nMen];
		womenPartnerName = new String[nWomen];
		isManEngaged = new boolean[nMen];
		isWomenEngaged = new boolean[nWomen];
		womenPartnerIndex = new int[nWomen];
		menPartnerIndex = new int[nMen];
		mansProposeCount= new int[nMen];
		womensProposeCount= new int[nWomen];
		womenToMenPartnerRank = new int[nWomen];
		menToWomenPartnerRank = new int[nMen];
		this.women = women;
		this.womenPreferece =  womenPreference;
		
		if(usersChoice == 1 || usersChoice == 3){
			this.menPreference = menPreference;
			this.men = men;
		}else{
			this.menPreference = reverseMenPreferenceArray(menPreference);
			this.men = reverseMenArray(men);
		}
		
		if(usersChoice == 1){
			System.out.println("\n\t1. MEN TO WOMEN IN NORMAL ORDER SELECTED");
			proposeWomen();
		}else if(usersChoice == 2 ){
			System.out.println("\n\t2. MEN TO WOMEN IN ( MEN IN ) REVERSE ORDER SELECTED");
			proposeWomen();
		}else{
			System.out.println("\n\t3. WOMEN TO MEN IN NORMAL ORDER SELECTED");			
			proposeMen();
		}
	}		// END OF CONSTRUCTOR
		
	//----------------------------------------------------
	// FUNCTION FOR MEN PROPOSING WOMEN
	//----------------------------------------------------
	public void proposeWomen(){
		while(menEngagedCount<nMen){
			for(int menCounter = 0 ; menCounter<men.length; menCounter++){
				for(int menPref= 0 ; menPref<menPreference[menCounter].length;menPref++){
					
					if(isManEngaged[menCounter]){
						break;	
					}else{
						int indexOfWomen = getIndexOfPartner(menPreference[menCounter][mansProposeCount[menCounter]]); 
							//	CHECK FOR WOMEN ENGAGED OR NOT FOR PAIR
							if(!isWomenEngaged[indexOfWomen]){
								makeCouple(menCounter,indexOfWomen);
								break;
							}else{
								hitOnEngaged(menCounter,indexOfWomen);
							}
					}	// END OF ELSE FOR IS MAN ENGAGED CONDITION
					
				}		// 	END OF MEN PREFERENCE LOOP
			}			//	END OF MEN FORR LOOP
		}				//  END OF WHILE LOOP
		printPairs();
	}					// END OF FUNCTION PROPOSEWOMEN 
		
	//----------------------------------------------------
	// FUNCTION FOR WOMEN PROPOSING MEN
	//----------------------------------------------------	
	public void proposeMen(){
		while(womenEngagedCount<nWomen){
			for(int womenCounter = 0 ; womenCounter < women.length; womenCounter++){
				for(int womenPref= 0 ; womenPref < womenPreferece[womenCounter].length ; womenPref++){
					if(isWomenEngaged[womenCounter]){
						break;
					}else{
						int indexOfMan = getIndexOfPartner(womenPreferece[womenCounter][womensProposeCount[womenCounter]]); 
							//	CHECK FOR MAN ENGAGED OR NOT FOR PAIR
							if(!isManEngaged[indexOfMan]){
								makeCouple(indexOfMan,womenCounter);
								continue;
							}else{
								hitOnEngaged(indexOfMan,womenCounter);
							}
					}	// END OF IS WOMEN ENGAGED CONDITION
				}		// 	END OF WOMEN PREFERENCE LOOP
			}			//	END OF WOMEN FOR LOOP
		}				//  END OF WHILE LOOP
		printPairs();
	}					// END OF FUNCTION PROPOSEMEN 

	//--------------------------------------------------------------------
	// FUNCTION TO FORM A COUPLE 
	//--------------------------------------------------------------------
	public void makeCouple(int manIndex,int womenIndex){
		isManEngaged[manIndex] = true;
		isWomenEngaged[womenIndex] = true;
		
		if(programMode == 1 || programMode == 2){
			womenPartnerIndex[womenIndex] = manIndex;
			womenPartnerName[womenIndex] = men[manIndex];
			updatePartnersRank(manIndex,womenIndex);
			menEngagedCount = menEngagedCount + 1;
		}else{
			menPartnerIndex[manIndex] = womenIndex;
			menPartnerName[manIndex] = women[womenIndex];
			updatePartnersRank(manIndex,womenIndex);
			womenEngagedCount = womenEngagedCount + 1;	
		}
	}

	//--------------------------------------------------------------------
	// FUNCTION TO DUMP MAN / WOMEN [ EXISTING PARTNER  ]
	//--------------------------------------------------------------------
	public void dumpPartner(int manIndex,int womenIndex){
		if(programMode == 1 || programMode == 2){
			isManEngaged[manIndex] = false;
			mansProposeCount[manIndex] = mansProposeCount[manIndex] + 1;
			menEngagedCount = menEngagedCount - 1;
		}else{
			isWomenEngaged[womenIndex] = false;
			womensProposeCount[womenIndex] = womensProposeCount[womenIndex] + 1;
			womenEngagedCount = womenEngagedCount - 1;			
		}
	}
		
	//--------------------------------------------------------------------
	// FUNCTION TO HIT ON ENGAGED WOMEN AND MEN
	//--------------------------------------------------------------------
	public void hitOnEngaged(int manIndex,int womenIndex){
		if(programMode == 1 || programMode == 2 ){
			int currentMansRank = getProposersRankInLine(manIndex, womenIndex);
			if(currentMansRank < womenToMenPartnerRank[womenIndex]){
				dumpPartner(womenPartnerIndex[womenIndex],womenIndex);
				makeCouple(manIndex,womenIndex);
			}else{
				mansProposeCount[manIndex] = mansProposeCount[manIndex] + 1; 
			}
		}else{
			int currentWomensRank = getProposersRankInLine(manIndex, womenIndex);			
			if(currentWomensRank < menToWomenPartnerRank[manIndex]){
				dumpPartner(womenPartnerIndex[womenIndex],womenIndex);
				makeCouple(manIndex,womenIndex);
			}else{
				womensProposeCount[manIndex] = womensProposeCount[manIndex] + 1; 
			}
		}
	}
	
	//--------------------------------------------------------------------
	// FUNCTION TO UPDATE PREFEERED PARTNERS RANK CURRENT PARTNER LIST  
	//--------------------------------------------------------------------	
	public void updatePartnersRank(int manIndex,int womenIndex){
		int rank = 0;
		if(programMode == 1 || programMode == 2){
			for (int i = 0 ; i< womenPreferece[womenIndex].length;i++){
				if(men[manIndex].equals(womenPreferece[womenIndex][i])){
					rank = i;
				}
			}
			womenToMenPartnerRank[womenIndex]= rank;
		}else{
			for (int i = 0 ; i< menPreference[manIndex].length;i++){
				if(women[womenIndex].equals(menPreference[manIndex][i])){
					rank = i;
				}
			}
			menToWomenPartnerRank[manIndex]= rank;
		}
	}

	//--------------------------------------------------------------------
	// FUNCTION TO FORM CHECK PROPOSERS RANK IN PREFERED LIST 
	//--------------------------------------------------------------------
	public int getProposersRankInLine(int manIndex,int womenIndex){
		int rank = 0;
		if(programMode == 1 || programMode == 2){		
			for (int i = 0 ; i< womenPreferece[womenIndex].length;i++){
				if(men[manIndex].equals(womenPreferece[womenIndex][i])){
					rank = i;
				}
			}
		}else{
			for (int i = 0 ; i< menPreference[manIndex].length;i++){
				if(women[womenIndex].equals(menPreference[manIndex][i])){
					rank = i;
				}
			}
		}
		return rank;
	}

	//--------------------------------------------------------------------
	// FUNCTIN TO GET INDEX OF WOMEN and MEN
	//--------------------------------------------------------------------
	public int getIndexOfPartner(String partnerName){
		int indexOfPartner = 0;
		if(programMode == 1 || programMode == 2){
			for(int i = 0 ; i < women.length; i++){
				if(partnerName.equals(women[i])){
					indexOfPartner = i;
				}
			}	
		}else{
			for(int i = 0 ; i < men.length; i++){
				if(partnerName.equals(men[i])){
					indexOfPartner = i;
				}
			}
		}
		return indexOfPartner;
	}
	
	//--------------------------------------------------------------------
	// FUNCTION TO PRINT COUPLE
	//--------------------------------------------------------------------	
	public void printPairs(){
		System.out.println();
		for (int i = 0; i< women.length; i++){
			if(programMode == 1 || programMode == 2){
				System.out.print ("\tCOUPLE "+(i+1)+"  : "+(women[i])+" "+(womenPartnerName[i]));
			}else{
				System.out.print ("\tCOUPLE "+(i+1)+"  : "+(men[i])+" "+(menPartnerName[i]));
			}
			System.out.println(" ");
		}
	}

	//--------------------------------------------------------------------
	// FUNCTION TO REVERSE MEN ARRAY
	//--------------------------------------------------------------------
	public String[] reverseMenArray(String[] men ){
		String temp;
		String[] reverseMen = new String[nMen];
		for (int i= 0, k = (men.length - 1); i < men.length; i++,k--){
				reverseMen[i] = men[k];
		}		
		return reverseMen;
	}
		
	//--------------------------------------------------------------------
	// FUNCTION TO REVERSE MEN PREFERENCE ARRAY
	//--------------------------------------------------------------------
	public  String[][] reverseMenPreferenceArray(String[][] menPreferece ){
		String[][] reverseMenPreferece = new String[nMen][nMen];
		for (int i= 0, k = (menPreferece[i].length - 1); i < menPreferece.length; i++,k--){
			for(int j = 0; j < menPreferece[i].length ;j++){
				reverseMenPreferece[k][j] = menPreferece[i][j];
			}
		}
		return reverseMenPreferece;
	}
}		// END OF CLASS