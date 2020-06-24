
public class ALUControl {

	String opcode;
	
	
	
	public String evaluateALU4Bit() {
		String bit4 = "";
		
			switch(opcode) {
			case("0000") : bit4 = "0010";break;
			case("0001") : bit4 = "0110";break;
			case("0010") : bit4 = "0011";break;   //TODO: Multiply , to be added in alu
			case("0011") : bit4 = "0000";break;
			case("0100") : bit4 = "0100";break;   //TODO: Multiply*2
			case("0101") : bit4 = "0101";break;  // TODO: Divide by 2
			case("0110") : bit4 = "0111";break;
			case("0111") : bit4 = "0010";break;    //TODO: Addi , requires more than 1 ALU4bit?
			case("1000") : bit4 = "0001";break;	   //TODO: Ori  , ????
			case("1001") : bit4 = "0010";break;
			case("1010") : bit4 = "0010";break;
			case("1011") : bit4 = "0110";break;	  //BNQ
			case("1100") : bit4 = "0110";break;   // BGT
			case("1101") : bit4 = "0010";break;
			}
		
		return bit4;
	}
}
