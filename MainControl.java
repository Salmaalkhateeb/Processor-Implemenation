import javax.management.ImmutableDescriptor;

public class MainControl {
	
	boolean UseRD;
	boolean WriteBack;
	boolean UseImmediateForALU;
	boolean PCtoBranch;
	boolean MemRead;
	boolean MemWrite;
	boolean MemToReg;
	boolean Jump;
	boolean SwitchImmR2;
	String type;
	
	
	public void evaluateSignals(String opcode) {
		switch(opcode) {
		case("0000"):	
		case("0001"):
		case("0010"):
		case("0011"):
		case("0100"):
		case("0101"):
		case("0110"):		
			
					UseImmediateForALU=false;
					MemToReg=false;
					WriteBack=true;
					MemRead=false;
					MemWrite=false;
					PCtoBranch=false;
					UseRD = true;
					SwitchImmR2 = false;
					break;
			
		
		case("1001"):
			
		UseImmediateForALU=true;
		MemToReg=true;
		WriteBack=true;
		MemRead=true;
		MemWrite=false;
		PCtoBranch=false;
		break;
		
		
		case("1010"):	
		UseImmediateForALU=true;
		MemToReg=false;
		WriteBack=true;
		MemRead=false;
		MemWrite=true;
		PCtoBranch=false;
		break;
		
		case("1011"):
		case("1100"):
			
		UseImmediateForALU=false;
		MemToReg=false;
		WriteBack=false;
		MemRead=false;
		MemWrite=false;
		PCtoBranch=true;
		break;
		
		
		case("1101"):
			
			UseImmediateForALU = false;
			MemToReg = false;
			WriteBack = false;
			MemRead = false;
			MemWrite = false;
			PCtoBranch = false;
			Jump = true;
			break;
			
		case("0111"):
		case("1000"):
			WriteBack = true;
		UseImmediateForALU = true;
		PCtoBranch = false;
		MemRead = false;
		MemWrite = false;
		MemToReg = false;
		break;

		}
	}
}
