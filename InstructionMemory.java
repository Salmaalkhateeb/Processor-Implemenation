import java.util.Hashtable;

public class InstructionMemory {
	
	int pc;
	Hashtable<String , Instruction> memory ;
	int Maxsize;
	int currentSize;
	int x2Counter;
	
	
	
	public InstructionMemory() {
		this.pc = 0;
		this.memory = new Hashtable<String, Instruction>();
		this.Maxsize = 1024;
		this.currentSize = 0;
		this.x2Counter = 0;
		System.out.println("Instruction memory created successfully of size " + 1024);
	}
		
		public void loadIntoInstructionMemory(String k) throws Exception {
			if(this.currentSize ==0) {
			this.x2Counter = 0;
			}
			else {
				this.x2Counter += 2;
			}
						
			String key = Integer.toBinaryString(this.x2Counter);
			
			
			if(currentSize < this.Maxsize) {
				
				this.memory.put(key,new Instruction(k));
				System.out.println("Intruction added to the memory hash");
				this.currentSize++;
			}
			else {
				System.out.println("Intruction Memory full , cant input more instructions");
			}
			
			
		}
		
		public String getInstructionType(Instruction s) throws Exception {
			
			String opcode = "";
			for(int i = 0 ; i<4 ; i++) {
				opcode += s.bits[i];
			}
			
			//TODO: Adjust instruction types when we netefe2 3la format
			switch(opcode) {
			case("0000") : ;
			case("0010") : return "addi";
			case("0001") : return "beq";
			case("1000") : return "lw";
			case("1010") : return "sw";
			}
			throw new Exception("Not a known instruction type");
			//return "not a known instruction";
			
		}
		
		public Instruction getNextInstruction() {
			System.out.println("Accessing next instruction with PC " + pc);
			String key = Integer.toBinaryString(pc);
			Instruction i = memory.get(key);
			this.pc = pc +2;
			System.out.println("PC is incremented by 2 , and is now " + pc);
			return i;
		}
		
		public Instruction getNextInstruction(String Address) {
			int pc = Integer.parseInt(Address, 2);
			System.out.println("Accessing instruction with PC " + pc);
			String key = Integer.toBinaryString(pc);
			Instruction i = memory.get(key);
			this.pc = pc +2;
			System.out.println("PC is incremented by 2 , and is now " + this.pc);
			return i;
		}
		
	}
	
	
	


