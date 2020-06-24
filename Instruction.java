
public class Instruction {
		int ID;
		char [] bits;
		int length = 16;
		boolean WriteBack;
		boolean UseImmediateForALU;
		boolean PCtoBranch;
		boolean MemRead;
		boolean MemWrite;
		//boolean MemToReg;
		boolean Jump;
		String ALUOUT;
		String branchingAddress;
		String ALU4bitoperation;
		String type;
		String op;
		String r1;				//TODO: fill the instructions correctly when confirmed
		String r2;				//TODO: fill the instructions correctly when confirmed
		String immediate;       //TODO: fill the instructions correctly when confirmed
		String rd;	
		String address;						//TODO: fill the instructions correctly when confirmed
		boolean fetched;
		boolean decoded;
		boolean executed;
		boolean accessed;
		boolean writtenback;
		boolean tookturn;
		
		
		public Instruction() {
			this.bits = new char [length];
			for(int i = 0 ; i<this.length ; i++) {
				bits[i] = '0';
			}
			for(int i = 0 ; i<4 ; i++) {
				op += this.bits[i];
			}
			
		}
		
		public Instruction(String k) throws Exception {
			this.op = "";
			this.r1 = "";
			this.r2 = "";
			this.immediate = "";
			this.rd = "";
			this.address = "";
			this.fetched = false;
			this.decoded = false;
			this.executed = false;
			this.accessed = false;
			this.writtenback = false;
			this.tookturn = false;
			this.bits = new char [length];
			if(k.length() > this.length) {
				System.out.println("Instruction size more than 16");
				
			}
			else {
			for(int i = 0 ; i<16; i++) {
				
				if(k.charAt(i)!= '0' && k.charAt(i)!='1' ) {
					throw new Exception("\"Instruction contains an invalid bit\"");
					
				}
				else {
					this.bits[i] = k.charAt(i);
				}
			}
			}
			for(int i = 0 ; i<4 ; i++) {
				op += this.bits[i];
			}
			
		}
		
		public String getBits() {
			String bitss ="";
			for(int i = 0 ; i<this.bits.length ; i++) {
				bitss+= bits[i];
			}
			return bitss;
		}
		
		public void fillR1() {
			for(int i = 4 ; i<8 ; i++) {
				r1+= bits[i];
			}
		}
		
		public void fillR2() {
			for(int i = 8 ; i<12 ; i++) {
				r2+= bits[i];
			}
			
			
		}
		public void fillOffset() {
			for(int i = 8 ; i<12 ; i++) {
				immediate+= bits[i];
			}
			}
		
		public void fillRd() {
			for(int i = 12 ; i<16 ; i++) {
				rd+= bits[i];
			}
		}
		public void fillAddress() {
			for(int i = 4 ; i<16 ; i++) {
				address+= bits[i];
			}
		}
		
		//the methods should continue this way , they will be called once we know the instruction type , so that the instruction is fully set
		
}
